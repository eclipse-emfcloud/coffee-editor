/*!
 * Copyright (c) 2019-2020 EclipseSource and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0, or the MIT License which is
 * available at https://opensource.org/licenses/MIT.
 *
 * SPDX-License-Identifier: EPL-2.0 OR MIT
 */
import { JsonSchema7 } from '@jsonforms/core';

// TODO UI schemas should be stored in the workspace and handled by the modelserver instead of hard coded copies in code
export const controlUnitView = {
    type: 'VerticalLayout',
    elements: [
        {
            type: 'Label',
            text: 'Control Unit'
        },
        {
            type: 'Group',
            label: 'Processor',
            elements: [
                {
                    type: 'HorizontalLayout',
                    elements: [
                        {
                            type: 'VerticalLayout',
                            elements: [
                                {
                                    type: 'Control',
                                    label: 'Vendor',
                                    scope: '#/properties/processor/properties/vendor'
                                },
                                {
                                    type: 'Control',
                                    label: 'Clock Speed',
                                    scope: '#/properties/processor/properties/clockSpeed'
                                },
                                {
                                    type: 'Control',
                                    label: 'Number Of Cores',
                                    scope: '#/properties/processor/properties/numberOfCores'
                                }
                            ]
                        },
                        {
                            type: 'VerticalLayout',
                            elements: [
                                {
                                    type: 'Control',
                                    label: 'Socketconnector Type',
                                    scope: '#/properties/processor/properties/socketconnectorType'
                                },
                                {
                                    type: 'Control',
                                    label: 'Manufacturing Process',
                                    scope: '#/properties/processor/properties/manufactoringProcess'
                                },
                                {
                                    type: 'Control',
                                    label: 'Thermal Design Power',
                                    scope: '#/properties/processor/properties/thermalDesignPower'
                                }
                            ]
                        }
                    ]
                }
            ]
        },
        {
            type: 'Group',
            label: 'Display',
            elements: [
                {
                    type: 'HorizontalLayout',
                    elements: [
                        {
                            type: 'Control',
                            label: 'Width',
                            scope: '#/properties/display/properties/width'
                        },
                        {
                            type: 'Control',
                            label: 'Height',
                            scope: '#/properties/display/properties/height'
                        }
                    ]
                }
            ]
        },
        {
            type: 'Group',
            label: 'Dimension',
            elements: [
                {
                    type: 'HorizontalLayout',
                    elements: [
                        {
                            type: 'Control',
                            label: 'Width',
                            scope: '#/properties/dimension/properties/width'
                        },
                        {
                            type: 'Control',
                            label: 'Height',
                            scope: '#/properties/dimension/properties/height'
                        },
                        {
                            type: 'Control',
                            label: 'Length',
                            scope: '#/properties/dimension/properties/length'
                        }
                    ]
                }
            ]
        },
        {
            type: 'Group',
            label: 'RAM',
            elements: [
                {
                    type: 'Control',
                    label: 'RAM',
                    scope: '#/properties/ram'
                }
            ]
        },
        // TODO[controlunit.ram array] - custom UI schema for ram array does not work as expected
        // {
        //     type: 'Group',
        //     label: 'RAM',
        //     elements: [
        //         {
        //             type: 'Control',
        //             scope: '#/properties/ram',
        //             options: {
        //                 detail: {
        //                     type: 'HorizontalLayout',
        //                     elements: [
        //                         {
        //                             type: 'Control',
        //                             label: 'Clock Speed',
        //                             scope: '#/properties/clockSpeed'
        //                         },
        //                         {
        //                             type: 'Control',
        //                             label: 'Size',
        //                             scope: '#/properties/size'
        //                         },
        //                         {
        //                             type: 'Control',
        //                             label: type,
        //                             scope: '#/properties/type'
        //                         }
        //                     ]
        //                 }
        //             }
        //         }
        //     ]
        // },
        {
            type: 'Group',
            label: 'Dimension',
            elements: [
                {
                    type: 'Control',
                    label: 'User Description',
                    scope: '#/properties/userDescription'
                }
            ]
        }
    ]
};

export const machineView = {
    type: 'VerticalLayout',
    elements: [
        {
            type: 'Label',
            text: 'Machine'
        },
        {
            type: 'Control',
            label: 'Name',
            scope: '#/properties/name'
        }
    ]
};

export const brewingView = {
    type: 'Label',
    text: 'Brewing Unit'
};
export const dipTrayView = {
    type: 'Label',
    text: 'Dip Tray'
};

export const waterTankView = {
    type: 'Label',
    text: 'Water Tank'
};
export const flowView = {
    type: 'Label',
    text: 'Flow'
};
export const weightedFlowView = {
    type: 'VerticalLayout',
    elements: [
        {
            type: 'Label',
            text: 'Weighted Flow'
        },
        {
            type: 'Control',
            scope: '#/properties/probability',
            options: { focus: true }
        }
    ]
};
export const mergeView = {
    type: 'Label',
    text: 'Merge'
};
export const decisionView = {
    type: 'Label',
    text: 'Decision'
};

export const manualTaskView = {
    type: 'VerticalLayout',
    elements: [
        {
            type: 'Label',
            text: 'Manual Task'
        },
        {
            type: 'Control',
            label: 'Name',
            scope: '#/properties/name',
            options: { focus: true }
        },
        {
            type: 'Control',
            label: 'Duration',
            scope: '#/properties/duration'
        },
        {
            type: 'Control',
            label: 'Actor',
            scope: '#/properties/actor'
        }
    ]
};

export const automaticTaskView = {
    type: 'VerticalLayout',
    elements: [
        {
            type: 'Label',
            text: 'Automatic Task'
        },
        {
            type: 'Control',
            label: 'Name',
            scope: '#/properties/name',
            options: { focus: true }
        },
        {
            type: 'Control',
            label: 'Duration',
            scope: '#/properties/duration'
        }
    ]
};

export const workflowView = {
    type: 'VerticalLayout',
    elements: [
        {
            type: 'Label',
            text: 'Workflow'
        },
        {
            type: 'Control',
            label: 'Name',
            scope: '#/properties/name',
            options: { focus: true }
        }
    ]
};

// TODO JSONSchema should be fetched from the modelserver instead of hard coded copy in code
export const coffeeSchema: JsonSchema7 = {
    'definitions': {
        'component': {
            '$id': '#component',
            'title': 'Component',
            'type': 'object',
            'properties': {
                'eClass': {
                    'const': 'http://www.eclipse.org/emfcloud/coffee/model#//Component'
                },
                'children': {
                    'type': 'array',
                    'items': {
                        '$ref': '#/definitions/component'
                    }
                },
                'parent': {
                    '$ref': '#/definitions/component'
                }
            },
            'additionalProperties': false
        },
        'machine': {
            '$id': '#machine',
            'title': 'Machine',
            'type': 'object',
            'properties': {
                'eClass': {
                    'const': 'http://www.eclipse.org/emfcloud/coffee/model#//Machine'
                },
                'children': {
                    'type': 'array',
                    'items': {
                        '$ref': '#/definitions/component'
                    }
                },
                'parent': {
                    '$ref': '#/definitions/component'
                },
                'name': {
                    'type': 'string'
                },
                'workflows': {
                    'type': 'array',
                    'items': {
                        '$ref': '#/definitions/workflow'
                    }
                }
            },
            'additionalProperties': false,
            'required': [
                'name'
            ]
        },
        'controlunit': {
            '$id': '#controlunit',
            'title': 'ControlUnit',
            'type': 'object',
            'properties': {
                'eClass': {
                    'const': 'http://www.eclipse.org/emfcloud/coffee/model#//ControlUnit'
                },
                'children': {
                    'type': 'array',
                    'items': {
                        '$ref': '#/definitions/component'
                    }
                },
                'parent': {
                    '$ref': '#/definitions/component'
                },
                'processor': {
                    '$ref': '#/definitions/processor'
                },
                'dimension': {
                    '$ref': '#/definitions/dimension'
                },
                'ram': {
                    'type': 'array',
                    'items': {
                        // TODO[controlunit.ram array]
                        // temporarily disable ref to ram object as it causes problems with the JSON forms array functionality for some reason
                        // '$ref': '#/definitions/ram',
                        'title': 'RAM',
                        'type': 'object',
                        'properties': {
                            'eClass': {
                                'const': 'http://www.eclipse.org/emfcloud/coffee/model#//RAM'
                            },
                            'clockSpeed': {
                                'type': 'integer'
                            },
                            'size': {
                                'type': 'integer'
                            },
                            'type': {
                                'type': 'string',
                                'enum': [
                                    'SO-DIMM',
                                    'SI-DIMM'
                                ]
                            }
                        },
                        'additionalProperties': false
                    }
                },
                'display': {
                    '$ref': '#/definitions/display'
                },
                'userDescription': {
                    'type': 'string'
                }
            },
            'additionalProperties': false
        },
        'brewingunit': {
            '$id': '#brewingunit',
            'title': 'BrewingUnit',
            'type': 'object',
            'properties': {
                'eClass': {
                    'const': 'http://www.eclipse.org/emfcloud/coffee/model#//BrewingUnit'
                },
                'children': {
                    'type': 'array',
                    'items': {
                        '$ref': '#/definitions/component'
                    }
                },
                'parent': {
                    '$ref': '#/definitions/component'
                }
            },
            'additionalProperties': false
        },
        'diptray': {
            '$id': '#diptray',
            'title': 'DipTray',
            'type': 'object',
            'properties': {
                'eClass': {
                    'const': 'http://www.eclipse.org/emfcloud/coffee/model#//DipTray'
                },
                'children': {
                    'type': 'array',
                    'items': {
                        '$ref': '#/definitions/component'
                    }
                },
                'parent': {
                    '$ref': '#/definitions/component'
                }
            },
            'additionalProperties': false
        },
        'watertank': {
            '$id': '#watertank',
            'title': 'WaterTank',
            'type': 'object',
            'properties': {
                'eClass': {
                    'const': 'http://www.eclipse.org/emfcloud/coffee/model#//WaterTank'
                },
                'children': {
                    'type': 'array',
                    'items': {
                        '$ref': '#/definitions/component'
                    }
                },
                'parent': {
                    '$ref': '#/definitions/component'
                }
            },
            'additionalProperties': false
        },
        'processor': {
            '$id': '#processor',
            'title': 'Processor',
            'type': 'object',
            'properties': {
                'eClass': {
                    'const': 'http://www.eclipse.org/emfcloud/coffee/model#//Processor'
                },
                'vendor': {
                    'type': 'string'
                },
                'clockSpeed': {
                    'type': 'integer'
                },
                'numberOfCores': {
                    'type': 'integer'
                },
                'socketconnectorType': {
                    '$ref': '#/definitions/socketconnectortype'
                },
                'thermalDesignPower': {
                    'type': 'integer'
                },
                'manufactoringProcess': {
                    '$ref': '#/definitions/manufactoringprocess'
                }
            },
            'additionalProperties': false
        },
        'socketconnectortype': {
            'type': 'string',
            'enum': [
                'A1T',
                'Z51'
            ]
        },
        'manufactoringprocess': {
            'type': 'string',
            'enum': [
                '18nm',
                'nm25'
            ]
        },
        'dimension': {
            '$id': '#dimension',
            'title': 'Dimension',
            'type': 'object',
            'properties': {
                'eClass': {
                    'const': 'http://www.eclipse.org/emfcloud/coffee/model#//Dimension'
                },
                'width': {
                    'type': 'integer'
                },
                'height': {
                    'type': 'integer'
                },
                'length': {
                    'type': 'integer'
                }
            },
            'additionalProperties': false
        },
        'ram': {
            '$id': '#ram',
            'title': 'RAM',
            'type': 'object',
            'properties': {
                'eClass': {
                    'const': 'http://www.eclipse.org/emfcloud/coffee/model#//RAM'
                },
                'clockSpeed': {
                    'type': 'integer'
                },
                'size': {
                    'type': 'integer'
                },
                'type': {
                    '$ref': '#/definitions/ramtype'
                }
            },
            'additionalProperties': false
        },
        'ramtype': {
            'type': 'string',
            'enum': [
                'SO-DIMM',
                'SI-DIMM'
            ]
        },
        'display': {
            '$id': '#display',
            'title': 'Display',
            'type': 'object',
            'properties': {
                'eClass': {
                    'const': 'http://www.eclipse.org/emfcloud/coffee/model#//Display'
                },
                'width': {
                    'type': 'integer'
                },
                'height': {
                    'type': 'integer'
                }
            },
            'additionalProperties': false
        },
        'workflow': {
            '$id': '#workflow',
            'title': 'Workflow',
            'type': 'object',
            'properties': {
                'eClass': {
                    'const': 'http://www.eclipse.org/emfcloud/coffee/model#//Workflow'
                },
                'name': {
                    'type': 'string'
                },
                'nodes': {
                    'type': 'array',
                    'items': {
                        '$ref': '#/definitions/node'
                    }
                },
                'flows': {
                    'type': 'array',
                    'items': {
                        '$ref': '#/definitions/flow'
                    }
                }
            },
            'additionalProperties': false,
            'required': [
                'name'
            ]
        },
        'node': {
            '$id': '#node',
            'title': 'Node',
            'type': 'object',
            'properties': {
                'eClass': {
                    'const': 'http://www.eclipse.org/emfcloud/coffee/model#//Node'
                }
            },
            'additionalProperties': false
        },
        'task': {
            '$id': '#task',
            'title': 'Task',
            'type': 'object',
            'properties': {
                'eClass': {
                    'const': 'http://www.eclipse.org/emfcloud/coffee/model#//Task'
                },
                'name': {
                    'type': 'string'
                },
                'duration': {
                    'type': 'integer'
                }
            },
            'additionalProperties': false,
            'required': [
                'name'
            ]
        },
        'automatictask': {
            '$id': '#automatictask',
            'title': 'AutomaticTask',
            'type': 'object',
            'properties': {
                'eClass': {
                    'const': 'http://www.eclipse.org/emfcloud/coffee/model#//AutomaticTask'
                },
                'name': {
                    'type': 'string'
                },
                'duration': {
                    'type': 'integer'
                },
                'component': {
                    '$ref': '#/definitions/component'
                }
            },
            'additionalProperties': false,
            'required': [
                'name'
            ]
        },
        'manualtask': {
            '$id': '#manualtask',
            'title': 'ManualTask',
            'type': 'object',
            'properties': {
                'eClass': {
                    'const': 'http://www.eclipse.org/emfcloud/coffee/model#//ManualTask'
                },
                'name': {
                    'type': 'string'
                },
                'duration': {
                    'type': 'integer'
                },
                'actor': {
                    'type': 'string'
                }
            },
            'additionalProperties': false,
            'required': [
                'name'
            ]
        },
        'fork': {
            '$id': '#fork',
            'title': 'Fork',
            'type': 'object',
            'properties': {
                'eClass': {
                    'const': 'http://www.eclipse.org/emfcloud/coffee/model#//Fork'
                }
            },
            'additionalProperties': false
        },
        'join': {
            '$id': '#join',
            'title': 'Join',
            'type': 'object',
            'properties': {
                'eClass': {
                    'const': 'http://www.eclipse.org/emfcloud/coffee/model#//Join'
                }
            },
            'additionalProperties': false
        },
        'decision': {
            '$id': '#decision',
            'title': 'Decision',
            'type': 'object',
            'properties': {
                'eClass': {
                    'const': 'http://www.eclipse.org/emfcloud/coffee/model#//Decision'
                }
            },
            'additionalProperties': false
        },
        'merge': {
            '$id': '#merge',
            'title': 'Merge',
            'type': 'object',
            'properties': {
                'eClass': {
                    'const': 'http://www.eclipse.org/emfcloud/coffee/model#//Merge'
                }
            },
            'additionalProperties': false
        },
        'flow': {
            '$id': '#flow',
            'title': 'Flow',
            'type': 'object',
            'properties': {
                'eClass': {
                    'const': 'http://www.eclipse.org/emfcloud/coffee/model#//Flow'
                },
                'source': {
                    '$ref': '#/definitions/node'
                },
                'target': {
                    '$ref': '#/definitions/node'
                }
            },
            'additionalProperties': false,
            'required': [
                'source',
                'target'
            ]
        },
        'weightedflow': {
            '$id': '#weightedflow',
            'title': 'WeightedFlow',
            'type': 'object',
            'properties': {
                'eClass': {
                    'const': 'http://www.eclipse.org/emfcloud/coffee/model#//WeightedFlow'
                },
                'source': {
                    '$ref': '#/definitions/node'
                },
                'target': {
                    '$ref': '#/definitions/node'
                },
                'probability': {
                    '$ref': '#/definitions/probability'
                }
            },
            'additionalProperties': false,
            'required': [
                'source',
                'target'
            ]
        },
        'probability': {
            'type': 'string',
            'enum': [
                'low',
                'medium',
                'high'
            ]
        }
    },
    '$ref': '#/definitions/machine'
};
