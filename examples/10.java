import java.util.Arrays;

public class PacketParser {
    private static final int HEADER_LENGTH = 5;
    private static final int FOOTER_LENGTH = 3;

    public byte[] getHeader(byte[] packet) {
        return Arrays.copyOfRange(packet, 0, HEADER_LENGTH);
    }

    public byte[] getPayload(byte[] packet) {
        if (packet.length <= HEADER_LENGTH + FOOTER_LENGTH) {
            throw new IllegalArgumentException("Packet size too small for valid payload");
        }
        return Arrays.copyOfRange(packet, HEADER_LENGTH, packet.length - FOOTER_LENGTH);
    }

    public byte[] getFooter(byte[] packet) {
        return Arrays.copyOfRange(packet, packet.length - FOOTER_LENGTH, packet.length);
    }

    public boolean isValidPacket(byte[] packet) {
        // For our simple case, a packet is valid if it has the correct total length
        // and the first byte of the header is not 0
        return packet.length >= HEADER_LENGTH + FOOTER_LENGTH && packet[0] != 0;
    }
}
