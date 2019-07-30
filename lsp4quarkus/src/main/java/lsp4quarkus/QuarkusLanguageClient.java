package lsp4quarkus;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.eclipse.lsp4j.jsonrpc.services.JsonRequest;
import org.eclipse.lsp4j.services.LanguageClient;

public interface QuarkusLanguageClient extends LanguageClient {

	@JsonRequest("quarkus/properties")
	CompletableFuture<List<ExtendedConfigDescriptionBuildItem>> getProperties();
}
