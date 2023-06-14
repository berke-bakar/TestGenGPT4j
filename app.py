# Import dependencies
import time
import os
import glob
import streamlit as st
from langchain.callbacks import get_openai_callback
from langchain.llms import OpenAI, OpenAIChat
from langchain.prompts import PromptTemplate
from langchain.chains import LLMChain


def get_example_options():
    path = r'./exampleCodes/*.java'
    files = glob.glob(path)
    return map(lambda name: name.split(os.sep)[-1], files)


def create_llm(api_key, api_type, model_name):
    if api_type == 'Standard API':
        return OpenAI(temperature=0.9, openai_api_key=api_key, model_name=model_name)
    else:
        return OpenAIChat(temperature=0.9, openai_api_key=api_key, model_name=model_name)


def create_example_text_area(selection):
    f = open(os.path.join("./exampleCodes", selection), "r")
    example_code = f.read()
    return st.text_area(label="Put your Java source file:", height=500,
                        placeholder="Enter Java code that has a class definition in it",
                        value=example_code, disabled=True)


def clicked():
    st.session_state['disabled'] = True


def enable():
    st.session_state['disabled'] = False


def initialize_state():
    if 'disabled' not in st.session_state:
        st.session_state['disabled'] = False
    if 'last_response' not in st.session_state:
        st.session_state['last_response'] = ""
    if 'error' not in st.session_state:
        st.session_state['error'] = False


def build_app():
    initialize_state()
    # Set title
    st.title("Test Generator for Java using ChatGPT")
    # Ask for OpenAI API key
    api_key = st.text_input("Enter your OpenAI API key:", type="password", key='api_key',
                            disabled=st.session_state.disabled)
    # Inform the user about where to get API key
    st.markdown("Get your OpenAI API key from [here](https://platform.openai.com/account/api-keys).")
    # Which API type to use
    api_type = st.radio(
        "Chose which OpenAI API to use:",
        ('Standard API', 'Chat API'), key='api_type', disabled=st.session_state.disabled)
    # Depending on the API type, ask about model name
    if api_type == "Standard API":
        model_name_options = ('text-davinci-002', 'text-davinci-003')
    else:
        model_name_options = ['gpt-3.5-turbo']

    model_name = st.radio(
        "Which model you want to use?",
        model_name_options, key='model_name', disabled=st.session_state.disabled)

    examples_available = st.checkbox('Use examples', key="use_examples", disabled=st.session_state.disabled)

    if examples_available:
        example_selection = st.selectbox(
            'Which example you want to use?',
            get_example_options(),
            key='example_selection', disabled=st.session_state.disabled)
        prompt = create_example_text_area(example_selection)
    else:
        # Text are for the prompt source code
        prompt = st.text_area(label="Put your Java source file:", height=500,
                              placeholder="Enter Java code that has a class definition in it",
                              disabled=st.session_state.disabled)
    # Submit button
    button = st.button("Generate", type="secondary", key='generate_button', on_click=clicked)
    st.divider()

    # Prompt template
    prompt_template = PromptTemplate(
        input_variables=['source_code'],
        template="Develop a JUnit5 unit test class that generates test methods for a given Java source code. The " +
                 "ultimate goal is to generate high-quality tests that achieve optimal decision coverage, " +
                 "statement coverage, control flow coverage, and data flow coverage. Additionally, employ test " +
                 "prioritization techniques as needed. Please provide your solution in code format exclusively, " +
                 "with no omissions. Here is the code:\n{source_code}"
    )

    llm = None
    code_chain = None
    # If an API key has been provided, create an OpenAI language model instance
    if api_key:
        llm = create_llm(api_key, api_type, model_name)
        code_chain = LLMChain(llm=llm, prompt=prompt_template, verbose=True)

    # if button clicked
    if button:
        # Could not create model
        if llm is None or code_chain is None:
            enable()
            st.session_state['error'] = True  # Show error
        else:
            # show a spinner until test are generated
            with st.spinner('Generating test...'):

                # Use max token for Standard API (text-davinci-002/003)
                if not isinstance(llm, OpenAIChat):
                    # It seems the API and tokenizer has a 77 token difference
                    # mentioned in a lot of forums but no official explanation
                    llm.max_tokens = llm.max_tokens_for_prompt(prompt) - 77

                # Measure time
                start_time = time.perf_counter()
                # Get response with additional info in the logs
                with get_openai_callback() as cb:
                    response = code_chain.run(source_code=prompt, verbose=True)
                    print(cb)
                end_time = time.perf_counter()
                elapsed_time_ms = int((end_time - start_time) * 1000)
                # Enable inputs
                enable()
                # Cache before render
                st.session_state['last_response'] = {
                    'response': response,
                    'elapsed_time': elapsed_time_ms,
                    'api': api_type,
                    'model': llm.model_name
                }
        # Render
        st.experimental_rerun()

    # Print cached response if available
    if st.session_state.last_response != "" and not st.session_state.error:
        st.write(f"**API:** {st.session_state.last_response['api']}")
        st.write(f"**Model name:** {st.session_state.last_response['model']}")
        st.write(f"**Elapsed time:** {st.session_state.last_response['elapsed_time']}ms")
        st.code(st.session_state.last_response['response'], language='java')
    if st.session_state.error:
        st.error("Could not create model, please check API key")
        st.session_state['error'] = False


if __name__ == '__main__':
    build_app()
