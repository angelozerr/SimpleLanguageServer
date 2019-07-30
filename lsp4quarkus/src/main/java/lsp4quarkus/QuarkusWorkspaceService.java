package lsp4quarkus;

import java.util.concurrent.CompletableFuture;
import java.util.logging.Logger;

import org.eclipse.lsp4j.DidChangeConfigurationParams;
import org.eclipse.lsp4j.DidChangeWatchedFilesParams;
import org.eclipse.lsp4j.ExecuteCommandParams;
import org.eclipse.lsp4j.services.WorkspaceService;

import com.google.gson.JsonPrimitive;

public class QuarkusWorkspaceService implements WorkspaceService {
	
	private static final Logger LOGGER = Logger.getLogger(QuarkusLanguageServer.class.getName());

	@Override
	public void didChangeConfiguration(DidChangeConfigurationParams params) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void didChangeWatchedFiles(DidChangeWatchedFilesParams params) {
		// TODO Auto-generated method stub
		
	}
	
    @Override
    public CompletableFuture<Object> executeCommand(ExecuteCommandParams params) {
    	
    	
    	
    	String command = ((JsonPrimitive)params.getArguments().get(0)).getAsString();
    	
        return CompletableFuture.supplyAsync(() -> {
        	switch (command) {
        		case "sendMetadata":
        			return "OK";
        		default:
        			return "NOT OK";
        	}
        });
    }

}
