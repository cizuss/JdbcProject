package ro.teamnet.zth.appl.domain;

import ro.teamnet.zth.api.annotations.*;

/**
 * Created by user on 7/7/2016.
 */
@Table(name = "departments")
public class Department  {
    @Id(name = "department_id")
    private Long id;
    @Column(name = "department_name")
    private String departmentName;
    @Column(name = "location_id")
    private Integer location;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public int getLocation() {
        return location;
    }

    public void setLocation(int location) {
        this.location = location;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Department)) return false;

        Department that = (Department) o;

        if (!id.equals(that.id)) return false;
        if (!departmentName.equals(that.departmentName)) return false;
        return location.equals(that.location);

    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + departmentName.hashCode();
        result = 31 * result + location.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Department{" +
                "id=" + id +
                ", departmentName='" + departmentName + '\'' +
                ", location=" + location +
                '}';
    }
}
