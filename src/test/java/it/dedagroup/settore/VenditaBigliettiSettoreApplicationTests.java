package it.dedagroup.settore;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.dedagroup.settore.DTO.request.SettoreRequest;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@ContextConfiguration(classes = VenditaBigliettiSettoreApplication.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureMockMvc
@RequiredArgsConstructor
class VenditaBigliettiSettoreApplicationTests {

    @Autowired
    MockMvc mock;

    //aggiungiSettore
    @Test
    @Order(1)
    void testAggiuntaSettore() throws Exception{
        SettoreRequest request =new SettoreRequest();
        request.setNome("D1");
        request.setPosti(50);
        request.setIdLuogo(10);
        String json = new ObjectMapper().writeValueAsString(request);
        mock.perform(MockMvcRequestBuilders.post("/settore/aggiungiSettore")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andReturn();
    }
    @Test
    @Order(2)
    void testAggiuntaSettore_SenzaNome() throws Exception{
        SettoreRequest request =new SettoreRequest();
        request.setPosti(50);
        request.setIdLuogo(1);
        String json = new ObjectMapper().writeValueAsString(request);
        mock.perform(MockMvcRequestBuilders.post("/settore/aggiungiSettore")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andReturn();
    }
    @Test
    @Order(3)
    void testAggiuntaSettore_SenzaPosti() throws Exception{
        SettoreRequest request =new SettoreRequest();
        request.setNome("A1");
        request.setIdLuogo(1);

        String json = new ObjectMapper().writeValueAsString(request);
        mock.perform(MockMvcRequestBuilders.post("/settore/aggiungiSettore")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andReturn();
    }
    @Test
    @Order(4)
    void testAggiuntaSettore_SenzaLuogo() throws Exception{
        SettoreRequest request =new SettoreRequest();
        request.setNome("A1");
        request.setPosti(50);
        String json = new ObjectMapper().writeValueAsString(request);
        mock.perform(MockMvcRequestBuilders.post("/settore/aggiungiSettore")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andReturn();
    }

    //findByID
    @Test
    @Order(5)
    void testFindById_Found() throws Exception{
        String id = "1";
        mock.perform(MockMvcRequestBuilders.get("/settore/findById")
                        .param("id", id)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andReturn();
    }
    @Test
    @Order(6)
    void testFindById_NotFound() throws Exception{
        String id = "10";
        mock.perform(MockMvcRequestBuilders.get("/settore/findById")
                        .param("id", id)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andReturn();
    }
    @Test
    @Order(6)
    void testFindByIData_NotValid() throws Exception{
        String id = "B";
        mock.perform(MockMvcRequestBuilders.get("/settore/findById")
                        .param("id", id)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();
    }

    //findByIdEsistenti
    @Test
    @Order(7)
    void testFindByIdEsistenti() throws Exception{
        String id = "1";
        mock.perform(MockMvcRequestBuilders.get("/settore/findByIdEsistenti")
                        .param("id", id)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andReturn();
    }
    @Test
    @Order(8)
    void testFindByIdEsistenti_NotFound() throws Exception{
        //il settore con ID 6 nel Data.sql ha la variabiler isCancellato=true
        String id = "6";
        mock.perform(MockMvcRequestBuilders.get("/settore/findByIdEsistenti")
                        .param("id", id)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andReturn();
    }
    @Test
    @Order(9)
    void testFindByIdEsistenti_NotValidData() throws Exception{
        //il settore con ID 6 nel Data.sql ha la variabiler isCancellato=true
        String id = "6";
        mock.perform(MockMvcRequestBuilders.get("/settore/findByIdEsistenti")
                        .param("id", id)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andReturn();
    }

    //findByNomeEsistenti
    @Test
    @Order(10)
    void testFindByNomeEsistenti() throws Exception{
        String nome = "A1";
        mock.perform(MockMvcRequestBuilders.get("/settore/allByNome/{nome}", nome)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andReturn();
    }
    @Test
    @Order(11)
    void testFindByNomeEsistenti_Error() throws Exception{
        //"Anello3" è presente nel Data.sql ma non lo trova perchè la variabile "isCalcellato" è true
        String nome = "Anello3";
        mock.perform(MockMvcRequestBuilders.get("/settore/allByNome/{nome}", nome)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andReturn();
    }

    //findAllByIds
    @Test
    @Order(12)
    void testAllByIds_Found() throws Exception{
        List<Long> listaID = new ArrayList<>();
        listaID.add(1L);
        listaID.add(3L);
        listaID.add(5L);
        String json = new ObjectMapper().writeValueAsString(listaID);
        mock.perform(MockMvcRequestBuilders.get("/settore/allByIds")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andReturn();
    }
    @Test
    @Order(13)
    void testAllByIds_NotFound() throws Exception{
        List<Long> listaID = new ArrayList<>();
        listaID.add(8L);
        listaID.add(9L);
        listaID.add(10L);
        String json = new ObjectMapper().writeValueAsString(listaID);
        mock.perform(MockMvcRequestBuilders.get("/settore/allByIds")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andReturn();
    }
    @Test
    @Order(14)
    void testAllByIds_ListaVuota() throws Exception{
        List<Long> listaID = new ArrayList<>();
        String json = new ObjectMapper().writeValueAsString(listaID);
        mock.perform(MockMvcRequestBuilders.get("/settore/allByIds")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();
    }
    @Test
    @Order(15)
    void testAllByIds_NotValidData() throws Exception{
        List<String> listaID = new ArrayList<>();
        listaID.add("a");
        listaID.add("b");
        String json = new ObjectMapper().writeValueAsString(listaID);
        mock.perform(MockMvcRequestBuilders.get("/settore/allByIds")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();
    }

    //findAllByPosti
    @Test
    @Order(16)
    void testFindAllByPosti_Found()throws Exception{
        int posti = 50;
        mock.perform(MockMvcRequestBuilders.get("/settore/allByPosti/{posti}", posti)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andReturn();
    }
    @Test
    @Order(17)
    void testFindAllByPosti_NotFound()throws Exception{
        int posti = 2;
        mock.perform(MockMvcRequestBuilders.get("/settore/allByPosti/{posti}", posti)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andReturn();
    }
    @Test
    @Order(18)
    void testFindAllByPosti_NotValidData()throws Exception{
        String posti = "a";
        mock.perform(MockMvcRequestBuilders.get("/settore/allByPosti/{posti}", posti)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();
    }

    //deleteSettore
    @Test
    @Order(19)
    void testDeleteSettoreById_Found() throws Exception{
        long id = 1;
        mock.perform(MockMvcRequestBuilders.post("/settore/deleteSettore/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andReturn();
    }
    @Test
    @Order(20)
    void teDeleteSettoreById_NotFound() throws Exception{
        long id = 30;
        mock.perform(MockMvcRequestBuilders.post("/settore/deleteSettore/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andReturn();
    }
    @Test
    @Order(21)
    void teDeleteSettoreById_NotValidData() throws Exception{
        String id = "a";
        mock.perform(MockMvcRequestBuilders.post("/settore/deleteSettore/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();
    }

    //findByIdLuogo
    @Test
    @Order(22)
    void testFindAllByIdLuogo_Found() throws Exception{
        String idLuogo = "1";
        mock.perform(MockMvcRequestBuilders.get("/settore/findAllByIdLuogo")
                        .param("idLuogo",idLuogo)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andReturn();
    }
    @Test
    @Order(23)
    void testFindAllByIdLuogo_NotFound() throws Exception{
        String idLuogo = "8";
        mock.perform(MockMvcRequestBuilders.get("/settore/findAllByIdLuogo")
                        .param("idLuogo",idLuogo)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andReturn();
    }
    @Test
    @Order(24)
    void testFindAllByIdLuogo_NotValidData() throws Exception{
        String idLuogo = "a";
        mock.perform(MockMvcRequestBuilders.get("/settore/findAllByIdLuogo")
                        .param("idLuogo",idLuogo)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();
    }
}
