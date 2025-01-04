package info.eati.web;

import info.eati.request.SimulacaoEmprestimoRequest;
import info.eati.response.ParcelaSimuladaResponse;
import info.eati.service.SimuladorEmprestimosService;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.jboss.resteasy.reactive.RestResponse;

import java.util.List;

@Path("/api/simulador-emprestimos")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class SimuladorEmprestimosResource {

    @Inject
    SimuladorEmprestimosService simuladorEmprestimosService;

    @POST
    public RestResponse<List<ParcelaSimuladaResponse>> simular(SimulacaoEmprestimoRequest request) {
        var resposta = simuladorEmprestimosService.simular(request);
        return RestResponse.ok(resposta);
    }
}
