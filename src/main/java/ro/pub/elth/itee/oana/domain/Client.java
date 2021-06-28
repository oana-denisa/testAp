package ro.pub.elth.itee.oana.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Client.
 */
@Entity
@Table(name = "client")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Client implements Serializable {

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

    @Column(name = "data_nastere")
    private Instant dataNastere;

    @NotNull
    @Size(max = 100)
    @Column(name = "adresa", length = 100, nullable = false)
    private String adresa;

    @NotNull
    @Size(min = 10)
    @Column(name = "telefon", nullable = false)
    private String telefon;

    @NotNull
    @Size(max = 50)
    @Column(name = "email", length = 50, nullable = false)
    private String email;

    @Column(name = "disponibilitate")
    private Boolean disponibilitate;

    @OneToOne
    @JoinColumn(unique = true)
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Client id(Long id) {
        this.id = id;
        return this;
    }

    public String getNume() {
        return this.nume;
    }

    public Client nume(String nume) {
        this.nume = nume;
        return this;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getPrenume() {
        return this.prenume;
    }

    public Client prenume(String prenume) {
        this.prenume = prenume;
        return this;
    }

    public void setPrenume(String prenume) {
        this.prenume = prenume;
    }

    public Instant getDataNastere() {
        return this.dataNastere;
    }

    public Client dataNastere(Instant dataNastere) {
        this.dataNastere = dataNastere;
        return this;
    }

    public void setDataNastere(Instant dataNastere) {
        this.dataNastere = dataNastere;
    }

    public String getAdresa() {
        return this.adresa;
    }

    public Client adresa(String adresa) {
        this.adresa = adresa;
        return this;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public String getTelefon() {
        return this.telefon;
    }

    public Client telefon(String telefon) {
        this.telefon = telefon;
        return this;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getEmail() {
        return this.email;
    }

    public Client email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getDisponibilitate() {
        return this.disponibilitate;
    }

    public Client disponibilitate(Boolean disponibilitate) {
        this.disponibilitate = disponibilitate;
        return this;
    }

    public void setDisponibilitate(Boolean disponibilitate) {
        this.disponibilitate = disponibilitate;
    }

    public User getUser() {
        return this.user;
    }

    public Client user(User user) {
        this.setUser(user);
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Client)) {
            return false;
        }
        return id != null && id.equals(((Client) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Client{" +
            "id=" + getId() +
            ", nume='" + getNume() + "'" +
            ", prenume='" + getPrenume() + "'" +
            ", dataNastere='" + getDataNastere() + "'" +
            ", adresa='" + getAdresa() + "'" +
            ", telefon='" + getTelefon() + "'" +
            ", email='" + getEmail() + "'" +
            ", disponibilitate='" + getDisponibilitate() + "'" +
            "}";
    }
}
