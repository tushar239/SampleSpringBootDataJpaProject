package sample.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Tushar Chokshi @ 7/20/17.
 */
@Entity
@Table(name = "customer")
public class Customer {
    @Id
    //@GeneratedValue
    @Column(name = "ID")
    private Long id;

    @Column(name = "PERSON_ID")
    private Long personId;

    public Customer() {
    }

    public Customer(Long id, Long personId) {
        this.id = id;
        this.personId = personId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Customer customer = (Customer) o;

        if (id != null ? !id.equals(customer.id) : customer.id != null) return false;
        return personId != null ? personId.equals(customer.personId) : customer.personId == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (personId != null ? personId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", personId=" + personId +
                '}';
    }
}
