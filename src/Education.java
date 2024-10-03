public class Education {
    private String degree;
    private String institution;
    private Date graduationDate;

    public Education(String degree, String institution, Date graduationDate) {
        this.degree = degree;
        this.institution = institution;
        this.graduationDate = graduationDate;
    }

    @Override
    public String toString() {
        return degree + " from " + institution + " (Graduated: " + graduationDate + ")";
    }
}
