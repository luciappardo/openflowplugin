package org.opendaylight.openflowplugin.testcommon;

public class DropTestStats {
    private final int rcvd;
    private final int sent;
    private final int excs;
    private final String message;

    public DropTestStats(int sent, int rcvd) {
        this.sent = sent;
        this.rcvd = rcvd;
        this.excs = 0;
        this.message = null;
    }

    public DropTestStats(int sent, int rcvd, int excs) {
        this.sent = sent;
        this.rcvd = rcvd;
        this.excs = excs;
        this.message = null;
    }

        public DropTestStats(String message) {
        this.sent = -1;
        this.rcvd = -1;
        this.excs = -1;
        this.message = message;
    }

    public int getSent() { return this.sent; }
    public int getRcvd() { return this.rcvd; }
    public String getMessage() { return this.message; }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        if (this.message == null) {
            result.append("Rcvd: ");
            result.append(this.rcvd);
            result.append(", Sent: ");
            result.append(this.sent);
            result.append("; Exceptions: ");
            result.append(this.excs);
        } else {
            result.append(this.message);
        }

        return result.toString();
    }

}
