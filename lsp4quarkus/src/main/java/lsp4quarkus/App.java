package lsp4quarkus;

import java.util.concurrent.Executors;
import java.util.function.Function;

import org.eclipse.lsp4j.jsonrpc.Launcher;
import org.eclipse.lsp4j.jsonrpc.MessageConsumer;
import org.eclipse.lsp4j.launch.LSPLauncher;
import org.eclipse.lsp4j.services.LanguageClient;

import lsp4quarkus.commons.ParentProcessWatcher;

public class App {
	public static void main(String[] args) {
    	QuarkusLanguageServer server = new QuarkusLanguageServer();
    	Function<MessageConsumer, MessageConsumer> wrapper;
    	wrapper = it -> it;
		if ("false".equals(System.getProperty("watchParentProcess"))) {
			wrapper = it -> it;
		} else {
			wrapper = new ParentProcessWatcher(server);
		}
    	Launcher<LanguageClient> launcher = LSPLauncher.createServerLauncher(
    											server,
    											System.in, 
    											System.out,
    											Executors.newCachedThreadPool(),
    											wrapper);
    	
    	server.setClient(launcher.getRemoteProxy());
    	launcher.startListening();
	}
}
