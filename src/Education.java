public class Education {
    private String degree;
    private String institution;
    private Date graduationDate;

    public Education(String degree, String institution, Date graduationDate) {
        this.degree = degree;
        this.institution = institution;
        this.graduationDate = graduationDate;
    }

    // Getter methods for each property
    public String getDegree() {
        return degree;
    }

    public String getInstitution() {
        return institution;
    }

    public Date getGraduationDate() {
        return graduationDate;
    }

    @Override
    public String toString() {
        return degree + " from " + institution + " (Graduated: " + graduationDate + ")";
    }
}
