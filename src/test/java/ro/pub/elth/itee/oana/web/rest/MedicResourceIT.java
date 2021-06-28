package ro.pub.elth.itee.oana.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import ro.pub.elth.itee.oana.IntegrationTest;
import ro.pub.elth.itee.oana.domain.Medic;
import ro.pub.elth.itee.oana.repository.MedicRepository;

/**
 * Integration tests for the {@link MedicResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MedicResourceIT {

    private static final String DEFAULT_NUME = "AAAAAAAAAA";
    private static final String UPDATED_NUME = "BBBBBBBBBB";

    private static final String DEFAULT_PRENUME = "AAAAAAAAAA";
    private static final String UPDATED_PRENUME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_DISPONIBILITATE = false;
    private static final Boolean UPDATED_DISPONIBILITATE = true;

    private static final String ENTITY_API_URL = "/api/medics";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private MedicRepository medicRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMedicMockMvc;

    private Medic medic;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Medic createEntity(EntityManager em) {
        Medic medic = new Medic().nume(DEFAULT_NUME).prenume(DEFAULT_PRENUME).disponibilitate(DEFAULT_DISPONIBILITATE);
        return medic;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Medic createUpdatedEntity(EntityManager em) {
        Medic medic = new Medic().nume(UPDATED_NUME).prenume(UPDATED_PRENUME).disponibilitate(UPDATED_DISPONIBILITATE);
        return medic;
    }

    @BeforeEach
    public void initTest() {
        medic = createEntity(em);
    }

    @Test
    @Transactional
    void createMedic() throws Exception {
        int databaseSizeBeforeCreate = medicRepository.findAll().size();
        // Create the Medic
        restMedicMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(medic)))
            .andExpect(status().isCreated());

        // Validate the Medic in the database
        List<Medic> medicList = medicRepository.findAll();
        assertThat(medicList).hasSize(databaseSizeBeforeCreate + 1);
        Medic testMedic = medicList.get(medicList.size() - 1);
        assertThat(testMedic.getNume()).isEqualTo(DEFAULT_NUME);
        assertThat(testMedic.getPrenume()).isEqualTo(DEFAULT_PRENUME);
        assertThat(testMedic.getDisponibilitate()).isEqualTo(DEFAULT_DISPONIBILITATE);
    }

    @Test
    @Transactional
    void createMedicWithExistingId() throws Exception {
        // Create the Medic with an existing ID
        medic.setId(1L);

        int databaseSizeBeforeCreate = medicRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMedicMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(medic)))
            .andExpect(status().isBadRequest());

        // Validate the Medic in the database
        List<Medic> medicList = medicRepository.findAll();
        assertThat(medicList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNumeIsRequired() throws Exception {
        int databaseSizeBeforeTest = medicRepository.findAll().size();
        // set the field null
        medic.setNume(null);

        // Create the Medic, which fails.

        restMedicMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(medic)))
            .andExpect(status().isBadRequest());

        List<Medic> medicList = medicRepository.findAll();
        assertThat(medicList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPrenumeIsRequired() throws Exception {
        int databaseSizeBeforeTest = medicRepository.findAll().size();
        // set the field null
        medic.setPrenume(null);

        // Create the Medic, which fails.

        restMedicMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(medic)))
            .andExpect(status().isBadRequest());

        List<Medic> medicList = medicRepository.findAll();
        assertThat(medicList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllMedics() throws Exception {
        // Initialize the database
        medicRepository.saveAndFlush(medic);

        // Get all the medicList
        restMedicMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(medic.getId().intValue())))
            .andExpect(jsonPath("$.[*].nume").value(hasItem(DEFAULT_NUME)))
            .andExpect(jsonPath("$.[*].prenume").value(hasItem(DEFAULT_PRENUME)))
            .andExpect(jsonPath("$.[*].disponibilitate").value(hasItem(DEFAULT_DISPONIBILITATE.booleanValue())));
    }

    @Test
    @Transactional
    void getMedic() throws Exception {
        // Initialize the database
        medicRepository.saveAndFlush(medic);

        // Get the medic
        restMedicMockMvc
            .perform(get(ENTITY_API_URL_ID, medic.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(medic.getId().intValue()))
            .andExpect(jsonPath("$.nume").value(DEFAULT_NUME))
            .andExpect(jsonPath("$.prenume").value(DEFAULT_PRENUME))
            .andExpect(jsonPath("$.disponibilitate").value(DEFAULT_DISPONIBILITATE.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingMedic() throws Exception {
        // Get the medic
        restMedicMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewMedic() throws Exception {
        // Initialize the database
        medicRepository.saveAndFlush(medic);

        int databaseSizeBeforeUpdate = medicRepository.findAll().size();

        // Update the medic
        Medic updatedMedic = medicRepository.findById(medic.getId()).get();
        // Disconnect from session so that the updates on updatedMedic are not directly saved in db
        em.detach(updatedMedic);
        updatedMedic.nume(UPDATED_NUME).prenume(UPDATED_PRENUME).disponibilitate(UPDATED_DISPONIBILITATE);

        restMedicMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedMedic.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedMedic))
            )
            .andExpect(status().isOk());

        // Validate the Medic in the database
        List<Medic> medicList = medicRepository.findAll();
        assertThat(medicList).hasSize(databaseSizeBeforeUpdate);
        Medic testMedic = medicList.get(medicList.size() - 1);
        assertThat(testMedic.getNume()).isEqualTo(UPDATED_NUME);
        assertThat(testMedic.getPrenume()).isEqualTo(UPDATED_PRENUME);
        assertThat(testMedic.getDisponibilitate()).isEqualTo(UPDATED_DISPONIBILITATE);
    }

    @Test
    @Transactional
    void putNonExistingMedic() throws Exception {
        int databaseSizeBeforeUpdate = medicRepository.findAll().size();
        medic.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMedicMockMvc
            .perform(
                put(ENTITY_API_URL_ID, medic.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(medic))
            )
            .andExpect(status().isBadRequest());

        // Validate the Medic in the database
        List<Medic> medicList = medicRepository.findAll();
        assertThat(medicList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMedic() throws Exception {
        int databaseSizeBeforeUpdate = medicRepository.findAll().size();
        medic.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMedicMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(medic))
            )
            .andExpect(status().isBadRequest());

        // Validate the Medic in the database
        List<Medic> medicList = medicRepository.findAll();
        assertThat(medicList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMedic() throws Exception {
        int databaseSizeBeforeUpdate = medicRepository.findAll().size();
        medic.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMedicMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(medic)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Medic in the database
        List<Medic> medicList = medicRepository.findAll();
        assertThat(medicList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMedicWithPatch() throws Exception {
        // Initialize the database
        medicRepository.saveAndFlush(medic);

        int databaseSizeBeforeUpdate = medicRepository.findAll().size();

        // Update the medic using partial update
        Medic partialUpdatedMedic = new Medic();
        partialUpdatedMedic.setId(medic.getId());

        partialUpdatedMedic.nume(UPDATED_NUME).prenume(UPDATED_PRENUME).disponibilitate(UPDATED_DISPONIBILITATE);

        restMedicMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMedic.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMedic))
            )
            .andExpect(status().isOk());

        // Validate the Medic in the database
        List<Medic> medicList = medicRepository.findAll();
        assertThat(medicList).hasSize(databaseSizeBeforeUpdate);
        Medic testMedic = medicList.get(medicList.size() - 1);
        assertThat(testMedic.getNume()).isEqualTo(UPDATED_NUME);
        assertThat(testMedic.getPrenume()).isEqualTo(UPDATED_PRENUME);
        assertThat(testMedic.getDisponibilitate()).isEqualTo(UPDATED_DISPONIBILITATE);
    }

    @Test
    @Transactional
    void fullUpdateMedicWithPatch() throws Exception {
        // Initialize the database
        medicRepository.saveAndFlush(medic);

        int databaseSizeBeforeUpdate = medicRepository.findAll().size();

        // Update the medic using partial update
        Medic partialUpdatedMedic = new Medic();
        partialUpdatedMedic.setId(medic.getId());

        partialUpdatedMedic.nume(UPDATED_NUME).prenume(UPDATED_PRENUME).disponibilitate(UPDATED_DISPONIBILITATE);

        restMedicMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMedic.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMedic))
            )
            .andExpect(status().isOk());

        // Validate the Medic in the database
        List<Medic> medicList = medicRepository.findAll();
        assertThat(medicList).hasSize(databaseSizeBeforeUpdate);
        Medic testMedic = medicList.get(medicList.size() - 1);
        assertThat(testMedic.getNume()).isEqualTo(UPDATED_NUME);
        assertThat(testMedic.getPrenume()).isEqualTo(UPDATED_PRENUME);
        assertThat(testMedic.getDisponibilitate()).isEqualTo(UPDATED_DISPONIBILITATE);
    }

    @Test
    @Transactional
    void patchNonExistingMedic() throws Exception {
        int databaseSizeBeforeUpdate = medicRepository.findAll().size();
        medic.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMedicMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, medic.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(medic))
            )
            .andExpect(status().isBadRequest());

        // Validate the Medic in the database
        List<Medic> medicList = medicRepository.findAll();
        assertThat(medicList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMedic() throws Exception {
        int databaseSizeBeforeUpdate = medicRepository.findAll().size();
        medic.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMedicMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(medic))
            )
            .andExpect(status().isBadRequest());

        // Validate the Medic in the database
        List<Medic> medicList = medicRepository.findAll();
        assertThat(medicList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMedic() throws Exception {
        int databaseSizeBeforeUpdate = medicRepository.findAll().size();
        medic.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMedicMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(medic)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Medic in the database
        List<Medic> medicList = medicRepository.findAll();
        assertThat(medicList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMedic() throws Exception {
        // Initialize the database
        medicRepository.saveAndFlush(medic);

        int databaseSizeBeforeDelete = medicRepository.findAll().size();

        // Delete the medic
        restMedicMockMvc
            .perform(delete(ENTITY_API_URL_ID, medic.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Medic> medicList = medicRepository.findAll();
        assertThat(medicList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
