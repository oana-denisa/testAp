package ro.pub.elth.itee.oana.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Specializare.
 */
@Entity
@Table(name = "specializare")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Specializare implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 80)
    @Column(name = "denumire", length = 80, nullable = false)
    private String denumire;

    @OneToMany(mappedBy = "specializare")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "user", "grad", "specializare" }, allowSetters = true)
    private Set<Medic> medics = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Specializare id(Long id) {
        this.id = id;
        return this;
    }

    public String getDenumire() {
        return this.denumire;
    }

    public Specializare denumire(String denumire) {
        this.denumire = denumire;
        return this;
    }

    public void setDenumire(String denumire) {
        this.denumire = denumire;
    }

    public Set<Medic> getMedics() {
        return this.medics;
    }

    public Specializare medics(Set<Medic> medics) {
        this.setMedics(medics);
        return this;
    }

    public Specializare addMedic(Medic medic) {
        this.medics.add(medic);
        medic.setSpecializare(this);
        return this;
    }

    public Specializare removeMedic(Medic medic) {
        this.medics.remove(medic);
        medic.setSpecializare(null);
        return this;
    }

    public void setMedics(Set<Medic> medics) {
        if (this.medics != null) {
            this.medics.forEach(i -> i.setSpecializare(null));
        }
        if (medics != null) {
            medics.forEach(i -> i.setSpecializare(this));
        }
        this.medics = medics;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Specializare)) {
            return false;
        }
        return id != null && id.equals(((Specializare) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Specializare{" +
            "id=" + getId() +
            ", denumire='" + getDenumire() + "'" +
            "}";
    }
}
