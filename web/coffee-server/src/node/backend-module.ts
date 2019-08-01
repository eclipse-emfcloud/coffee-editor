import { LaunchOptions } from "@modelserver/theia";
import { BackendApplicationContribution } from "@theia/core/lib/node/backend-application";
import { ContainerModule, injectable } from "inversify";
import { join, resolve } from "path";

import { GLSPLaunchOptions, GLSPServerLauncher } from "./glsp-server-launcher";


export default new ContainerModule(bind => {
    bind(LaunchOptions).to(CoffeeLaunchOptions).inSingletonScope();
    bind(GLSPLaunchOptions).to(CoffeeGlspLaunchOptions).inSingletonScope();
    bind(BackendApplicationContribution).to(GLSPServerLauncher);
});

@injectable()
export class CoffeeLaunchOptions implements LaunchOptions {
    isRunning = false;
    baseURL: string = "api/v1/";
    serverPort: number = 8081;
    hostname: string = "localhost";
    jarPath = resolve(join(__dirname, '..', '..', 'build', 'com.eclipsesource.modelserver.example-0.0.1-SNAPSHOT-standalone.jar'));
    additionalArgs = ["--errorsOnly"];
}

@injectable()
export class CoffeeGlspLaunchOptions implements GLSPLaunchOptions {
    isRunning: false;
    serverPort: 5008;
    hostname: "localhost";
    jarPath = resolve(join(__dirname, '..', '..', 'build', 'workflow-modelserver-example-1.2.0-SNAPSHOT-glsp.jar'));
}





