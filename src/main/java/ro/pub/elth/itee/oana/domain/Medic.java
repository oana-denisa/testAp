package ro.pub.elth.itee.oana.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Medic.
 */
@Entity
@Table(name = "medic")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Medic implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 50)
    @Column(name = "nume", length = 50, nullable = false)
    private String nume;

    @NotNull
    @Size(max = 50)
    @Column(name = "prenume", length = 50, nullable = false)
    private String prenume;

    @Column(name = "disponibilitate")
    private Boolean disponibilitate;

    @OneToOne
    @JoinColumn(unique = true)
    private User user;

    @OneToOne
    @JoinColumn(unique = true)
    private Grad grad;

    @ManyToOne
    @JsonIgnoreProperties(value = { "medics" }, allowSetters = true)
    private Specializare specializare;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Medic id(Long id) {
        this.id = id;
        return this;
    }

    public String getNume() {
        return this.nume;
    }

    public Medic nume(String nume) {
        this.nume = nume;
        return this;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getPrenume() {
        return this.prenume;
    }

    public Medic prenume(String prenume) {
        this.prenume = prenume;
        return this;
    }

    public void setPrenume(String prenume) {
        this.prenume = prenume;
    }

    public Boolean getDisponibilitate() {
        return this.disponibilitate;
    }

    public Medic disponibilitate(Boolean disponibilitate) {
        this.disponibilitate = disponibilitate;
        return this;
    }

    public void setDisponibilitate(Boolean disponibilitate) {
        this.disponibilitate = disponibilitate;
    }

    public User getUser() {
        return this.user;
    }

    public Medic user(User user) {
        this.setUser(user);
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Grad getGrad() {
        return this.grad;
    }

    public Medic grad(Grad grad) {
        this.setGrad(grad);
        return this;
    }

    public void setGrad(Grad grad) {
        this.grad = grad;
    }

    public Specializare getSpecializare() {
        return this.specializare;
    }

    public Medic specializare(Specializare specializare) {
        this.setSpecializare(specializare);
        return this;
    }

    public void setSpecializare(Specializare specializare) {
        this.specializare = specializare;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Medic)) {
            return false;
        }
        return id != null && id.equals(((Medic) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Medic{" +
            "id=" + getId() +
            ", nume='" + getNume() + "'" +
            ", prenume='" + getPrenume() + "'" +
            ", disponibilitate='" + getDisponibilitate() + "'" +
            "}";
    }
}
