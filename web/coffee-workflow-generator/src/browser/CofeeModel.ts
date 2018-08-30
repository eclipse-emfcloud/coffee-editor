export interface CoffeeModel {
    name: string;
    children?: CoffeeModel[];
    activities?: CoffeeModel[];
    eClass?: string;
}

export interface Workflow {
    revision: number;
    type: "graph";
    id: "sprotty";
    children: Object[];
}