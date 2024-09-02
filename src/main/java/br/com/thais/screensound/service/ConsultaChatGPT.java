package br.com.thais.screensound.service;

import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.service.OpenAiService;

public class ConsultaChatGPT {
	public static String obterInfoArtista(String texto) {
		OpenAiService service = new OpenAiService(System.getenv("OPEN_AI_KEY"));

		CompletionRequest requisicao = CompletionRequest.builder().model("gpt-3.5-turbo-instruct")
				.prompt("busque informações sobre este artista: " + texto).maxTokens(1000).temperature(0.7).build();

		var resposta = service.createCompletion(requisicao);
		return resposta.getChoices().get(0).getText();
	}
}
