package lsp4quarkus;

import static org.eclipse.lsp4j.jsonrpc.CompletableFutures.computeAsync;

import java.util.Collections;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Logger;

import org.eclipse.lsp4j.CompletionOptions;
import org.eclipse.lsp4j.InitializeParams;
import org.eclipse.lsp4j.InitializeResult;
import org.eclipse.lsp4j.ServerCapabilities;
import org.eclipse.lsp4j.TextDocumentSyncKind;
import org.eclipse.lsp4j.services.LanguageClient;
import org.eclipse.lsp4j.services.LanguageServer;
import org.eclipse.lsp4j.services.TextDocumentService;
import org.eclipse.lsp4j.services.WorkspaceService;

import lsp4quarkus.commons.ParentProcessWatcher.ProcessLanguageServer;

public class QuarkusLanguageServer implements LanguageServer, ProcessLanguageServer {
	
	private static final Logger LOGGER = Logger.getLogger(QuarkusLanguageServer.class.getName());
	
	private TextDocumentService textDocumentService;
	private WorkspaceService workspaceService;
	private Integer parentProcessId;
	private LanguageClient languageClient;
	
	public QuarkusLanguageServer() {
		textDocumentService = new QuarkusTextDocumentService();
		workspaceService = new QuarkusWorkspaceService();
	}

	public CompletableFuture<InitializeResult> initialize(InitializeParams params) {
		LOGGER.info("Inside initialize");
		this.parentProcessId = params.getProcessId();
		ServerCapabilities serverCapabilities = new ServerCapabilities();
		serverCapabilities.setTextDocumentSync(TextDocumentSyncKind.Incremental);
		serverCapabilities.setHoverProvider(false);
		serverCapabilities.setCompletionProvider(new CompletionOptions(false, Collections.emptyList()));
		serverCapabilities.setDefinitionProvider(false);
		serverCapabilities.setTypeDefinitionProvider(false);
		serverCapabilities.setImplementationProvider(false);
		serverCapabilities.setReferencesProvider(false);
		serverCapabilities.setDocumentHighlightProvider(false);
		serverCapabilities.setDocumentSymbolProvider(false);
		serverCapabilities.setWorkspaceSymbolProvider(false);
		serverCapabilities.setCodeActionProvider(false);
		serverCapabilities.setDocumentFormattingProvider(true);
		serverCapabilities.setDocumentRangeFormattingProvider(true);
		serverCapabilities.setRenameProvider(false);
		serverCapabilities.setColorProvider(false);
		serverCapabilities.setFoldingRangeProvider(false);
		serverCapabilities.setTypeHierarchyProvider(false);
		serverCapabilities.setCallHierarchyProvider(false);
		
		InitializeResult initializeResult = new InitializeResult(serverCapabilities);
		
		return CompletableFuture.completedFuture(initializeResult);
	}

	public CompletableFuture<Object> shutdown() {
		return computeAsync(cc -> new Object());
	}

	public void exit() {
		exit(0);
		
	}
	
	@Override
	public void exit(int exitCode) {
		System.exit(exitCode);
	}


	public TextDocumentService getTextDocumentService() {
		return this.textDocumentService;
	}

	public WorkspaceService getWorkspaceService() {
		return this.workspaceService;
	}
	
	public LanguageClient getLanguageClient() {
		return languageClient;
	}
	
	public void setClient(LanguageClient languageClient) {
		this.languageClient = languageClient;
	}

	@Override
	public long getParentProcessId() {
		return parentProcessId != null ? parentProcessId : 0;
	}

}
