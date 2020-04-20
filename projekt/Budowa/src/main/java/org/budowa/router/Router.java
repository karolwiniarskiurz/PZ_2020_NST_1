package org.budowa.router;

import org.budowa.flow.Flow;
import org.budowa.flow.FlowsRegistry;
import org.budowa.services.SceneManager;
import org.budowa.services.SessionManager;

import java.io.IOException;

/**
 * Singleton
 * Injectable class
 */
public class Router {
    //region Static methods
    private static Router router;

    public static Router inject() {
        if (Router.router == null) {
            Router.router = new Router();
        }
        return Router.router;
    }
    //endregion

    private final SceneManager sceneManager = SceneManager.inject();

    private final SessionManager sessionManager = SessionManager.inject();

    private Route currentRoute;

    public void goTo(Route route) throws IOException {
        if (route == currentRoute) {
            return;
        }
        switch (route) {
            case LOGIN: {
                // todo: check if logged in sessionManager
                if (this.sessionManager.isLoggedIn()) {
                    return;
                }
                RouteData routeData = Routes.getRouteData(Route.LOGIN);
                var sceneName = FlowsRegistry.getFXML(Flow.LOGIN);
                this.sceneManager.createScene(sceneName, routeData.title);
                break;
            }
            case DASHBOARD: {
                if (!this.sessionManager.isLoggedIn()) {
                    // todo: throw unauthorized exception or some other custom exception
                    return;
                }
                var userRole = this.sessionManager.getUserRole();
                var routeData = Routes.getRouteData(Route.DASHBOARD);
                String fxml;
                switch (userRole) {
                    case OWNER:
                        fxml = FlowsRegistry.getFXML(Flow.OWNER_DASHBOARD);
                        break;
                    case MANAGER:
                        fxml = FlowsRegistry.getFXML(Flow.MANAGER_DASHBOARD);
                        break;
                    default:
                        fxml = FlowsRegistry.getFXML(Flow.WORKER_DASHBOARD);
                }
                this.sceneManager.createScene(fxml, routeData.title);
            }

        }
        this.currentRoute = route;
    }
}
