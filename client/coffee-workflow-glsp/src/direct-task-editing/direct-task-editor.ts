/*
 * Copyright (c) 2020-2022 EclipseSource and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0, or the MIT License which is
 * available at https://opensource.org/licenses/MIT.
 *
 * SPDX-License-Identifier: EPL-2.0 OR MIT
 */
import {
    AbstractUIExtension,
    Action,
    AutoCompleteWidget,
    EditorContextService,
    getAbsoluteClientBounds,
    GLSPActionDispatcher,
    hasStringProp,
    ILogger,
    LabeledAction,
    ModelIndexImpl,
    Operation,
    RequestContextActions,
    RequestEditValidationAction,
    SetContextActions,
    SetEditValidationResultAction,
    SModelRoot,
    toActionArray,
    TYPES,
    ValidationDecorator,
    ValidationStatus,
    ViewerOptions
} from '@eclipse-glsp/client';
import { inject, injectable } from 'inversify';
import { DOMHelper } from 'sprotty/lib/base/views/dom-helper';
import { isTaskNode, TaskNode } from '../model';

/**
 * Is send from the {@link TaskEditor} to the GLSP server
 * to execute a task edit operation.
 */
export interface ApplyTaskEditOperation extends Operation {
    kind: typeof ApplyTaskEditOperation.KIND;

    /**
     * Id of the task that should be edited
     */
    taskId: string;

    /**
     * The edit expression
     */
    expression: string;
}

export namespace ApplyTaskEditOperation {
    export const KIND = 'applyTaskEdit';

    export function is(object: any): object is ApplyTaskEditOperation {
        return Operation.hasKind(object, KIND) && hasStringProp(object, 'taskId') && hasStringProp(object, 'expression');
    }

    export function create(options: { taskId: string; expression: string }): ApplyTaskEditOperation {
        return {
            kind: KIND,
            isOperation: true,
            ...options
        };
    }
}

@injectable()
export class TaskEditor extends AbstractUIExtension {
    static readonly ID = 'task-editor';
    readonly autoSuggestionSettings = {
        noSuggestionsMessage: 'No suggestions available',
        suggestionsClass: 'command-palette-suggestions',
        debounceWaitMs: 50,
        showOnFocus: true
    };

    @inject(TYPES.IActionDispatcher)
    protected actionDispatcher: GLSPActionDispatcher;

    @inject(EditorContextService)
    protected editorContextService: EditorContextService;

    @inject(TYPES.ViewerOptions)
    protected viewerOptions: ViewerOptions;

    @inject(TYPES.DOMHelper)
    protected domHelper: DOMHelper;

    @inject(TYPES.ILogger)
    protected override logger: ILogger;

    protected task: TaskNode;
    protected autoSuggestion: AutoCompleteWidget;

    id(): string {
        return TaskEditor.ID;
    }
    containerClass(): string {
        return 'command-palette';
    }

    protected initializeContents(containerElement: HTMLElement): void {
        this.autoSuggestion = new AutoCompleteWidget(
            this.autoSuggestionSettings,
            { provideSuggestions: input => this.retrieveSuggestions(input) },
            { executeFromSuggestion: input => this.executeFromSuggestion(input) },
            () => this.hide(),
            this.logger
        );
        this.autoSuggestion.configureValidation(
            { validate: input => this.validateInput(input) },
            new ValidationDecorator(containerElement)
        );
        this.autoSuggestion.configureTextSubmitHandler({
            executeFromTextOnlyInput: (input: string) => this.executeFromTextOnlyInput(input)
        });
        this.autoSuggestion.initialize(containerElement);
    }

    override show(root: Readonly<SModelRoot>, ...contextElementIds: string[]): void {
        super.show(root, ...contextElementIds);
        this.autoSuggestion.open(root);
    }

    protected override onBeforeShow(containerElement: HTMLElement, root: Readonly<SModelRoot>, ...contextElementIds: string[]): void {
        this.task = getTask(contextElementIds, root.index)[0];
        this.autoSuggestion.inputField.value = '';
        this.setPosition(containerElement);
    }

    protected setPosition(containerElement: HTMLElement): void {
        let x = 0;
        let y = 0;

        if (this.task) {
            const bounds = getAbsoluteClientBounds(this.task, this.domHelper, this.viewerOptions);
            x = bounds.x + 5;
            y = bounds.y + 5;
        }

        containerElement.style.left = `${x}px`;
        containerElement.style.top = `${y}px`;
        containerElement.style.width = '200px';
    }

    protected async retrieveSuggestions(input: string): Promise<LabeledAction[]> {
        const response = await this.actionDispatcher.request(
            RequestContextActions.create({ contextId: TaskEditor.ID, editorContext: this.editorContextService.get({ ['text']: input }) })
        );
        if (SetContextActions.is(response)) {
            return response.actions;
        }
        return Promise.reject();
    }

    protected async validateInput(input: string): Promise<ValidationStatus> {
        const response = await this.actionDispatcher.request(
            RequestEditValidationAction.create({ contextId: TaskEditor.ID, modelElementId: this.task.id, text: input })
        );
        if (SetEditValidationResultAction.is(response)) {
            return response.status;
        }
        return Promise.reject();
    }

    protected executeFromSuggestion(input: LabeledAction | Action[] | Action): void {
        this.actionDispatcher.dispatchAll(toActionArray(input));
    }

    protected executeFromTextOnlyInput(input: string): void {
        const action = ApplyTaskEditOperation.create({ taskId: this.task.id, expression: input });
        this.actionDispatcher.dispatch(action);
    }

    override hide(): void {
        this.autoSuggestion.dispose();
        super.hide();
    }
}

function getTask(ids: string[], index: ModelIndexImpl): TaskNode[] {
    return ids.map(id => index.getById(id)).filter(element => element && isTaskNode(element)) as TaskNode[];
}
