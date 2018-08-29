import { ContainerModule } from "inversify";
import { WorkspaceStorageServiceFilesystem } from "./workspace-storage-service-filesystem";

export default new ContainerModule(bind => {
    bind(WorkspaceStorageServiceFilesystem).toSelf().inSingletonScope();
});
