package model;

/**
 * Created by luiz on 25/11/15.
 */
public class Software {

    private long uuid;
    private String name;
    private int releaseVersion;
    private int minorVersion;
    private int fixVersion;
    private boolean deprecate;

    public Software() {
    }

    public long getUuid() {
        return uuid;
    }

    public void setUuid(long uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getReleaseVersion() {
        return releaseVersion;
    }

    public void setReleaseVersion(int releaseVersion) {
        this.releaseVersion = releaseVersion;
    }

    public int getMinorVersion() {
        return minorVersion;
    }

    public void setMinorVersion(int minorVersion) {
        this.minorVersion = minorVersion;
    }

    public int getFixVersion() {
        return fixVersion;
    }

    public void setFixVersion(int fixVersion) {
        this.fixVersion = fixVersion;
    }

    public boolean isDeprecate() {
        return deprecate;
    }

    public void setDeprecate(boolean deprecate) {
        this.deprecate = deprecate;
    }

    public void newRelease(){
        releaseVersion =+ 1;
    }

    public void newMinor(){
        minorVersion =+ 1;
    }

    public void newFix(){
        fixVersion += 1;
    }

    public String toString(){
        return uuid + " | " + name + " | " + releaseVersion + "." + minorVersion + "." + fixVersion;
    }

}
