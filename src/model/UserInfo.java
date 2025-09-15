package model;
import java.math.BigDecimal;
import java.sql.Date;

public class UserInfo {
  private int userId;
  private String firstName;
  private String lastName;
  private String companyName;
  private BigDecimal salary;
  private Date startDate;

  public int getUserId() { return userId; }
  public void setUserId(int userId) { this.userId = userId; }

  public String getFirstName() { return firstName; }
  public void setFirstName(String firstName) { this.firstName = firstName; }

  public String getLastName() { return lastName; }
  public void setLastName(String lastName) { this.lastName = lastName; }

  public String getCompanyName() { return companyName; }
  public void setCompanyName(String companyName) { this.companyName = companyName; }

  public BigDecimal getSalary() { return salary; }
  public void setSalary(BigDecimal salary) { this.salary = salary; }

  public Date getStartDate() { return startDate; }
  public void setStartDate(Date startDate) { this.startDate = startDate; }
}
