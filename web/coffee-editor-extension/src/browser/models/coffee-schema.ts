const controlUnitView = {
  'type': 'VerticalLayout',
  'elements': [
    {
      'type': 'Group',
      'label': 'Processor',
      'elements': [
        {
          'type': 'HorizontalLayout',
          'elements': [
            {
              'type': 'VerticalLayout',
              'elements': [
                {
                  'type': 'Control',
                  'label': 'Vendor',
                  'scope': '#/properties/processor/properties/vendor'
                },
                {
                  'type': 'Control',
                  'label': 'Clock Speed',
                  'scope': '#/properties/processor/properties/clockSpeed'
                },
                {
                  'type': 'Control',
                  'label': 'Number Of Cores',
                  'scope': '#/properties/processor/properties/numberOfCores'
                }
              ]
            },
            {
              'type': 'VerticalLayout',
              'elements': [
                {
                  'type': 'Control',
                  'label': 'Socketconnector Type',
                  'scope': '#/properties/processor/properties/socketconnectorType'
                },
                {
                  'type': 'Control',
                  'label': 'Manufactoring Process',
                  'scope': '#/properties/processor/properties/manufactoringProcess'
                },
                {
                  'type': 'Control',
                  'label': 'Thermal Design Power',
                  'scope': '#/properties/processor/properties/thermalDesignPower'
                }
              ]
            }
          ]
        }
      ]
    },
    {
      'type': 'Group',
      'label': 'Display',
      'elements': [
        {
          'type': 'HorizontalLayout',
          'elements': [
            {
              'type': 'Control',
              'label': 'Width',
              'scope': '#/properties/display/properties/width'
            },
            {
              'type': 'Control',
              'label': 'Height',
              'scope': '#/properties/display/properties/height'
            }
          ]
        }
      ]
    },
    {
      'type': 'Group',
      'label': 'Dimension',
      'elements': [
        {
          'type': 'HorizontalLayout',
          'elements': [
            {
              'type': 'Control',
              'label': 'Width',
              'scope': '#/properties/dimension/properties/width'
            },
            {
              'type': 'Control',
              'label': 'Height',
              'scope': '#/properties/dimension/properties/height'
            },
            {
              'type': 'Control',
              'label': 'Length',
              'scope': '#/properties/dimension/properties/length'
            }
          ]
        }
      ]
    },
    {
      'type': 'Control',
      'label': 'Ram',
      'scope': '#/properties/ram'
    },
    {
      'type': 'Control',
      'label': 'Activities',
      'scope': '#/properties/activities'
    },
    {
      'type': 'Group',
      'label': 'User Description',
      'elements': [
        {
          'type': 'Control',
          'label': 'User Description',
          'scope': '#/properties/userDescription'
        }
      ]
    }
  ]
};

const machineView = {
  'type': 'VerticalLayout',
  'elements': [
    {
      'type': 'Control',
      'label': 'Name',
      'scope': '#/properties/name'
    }
  ]
};

export const coffeeSchema = {
  'definitions': {
    'activity': {
      '$id': '#activity',
      'label': 'Activity',
      'type': 'object',
      'properties': {
        'name': {
          'type': 'string'
        }
      },
      'additionalProperties': false,
      'required': [
        'name'
      ]
    },
    'brewingunit': {
      '$id': '#brewingunit',
      'label': 'Brewing Unit',
      'properties': {
        'eClass': {
          'type': 'string',
          'default': 'http://www.eclipse.org/emfforms/example/coffeemodel#//BrewingUnit'
        },
        'activities': {
          'type': 'array',
          'items': {
            '$ref': '#/definitions/activity'
          }
        },
        'children': {
          'type': 'array',
          'items': {
            'anyOf': [
              { '$ref': '#/definitions/brewingunit'},
              { '$ref': '#/definitions/controlunit'},
              { '$ref': '#/definitions/diptray'},
              { '$ref': '#/definitions/watertank'}
            ]
          }
        }
      },
      'additionalProperties': false
    },
    'controlunit': {
      '$id': '#controlunit',
      'label': 'Control Unit',
      'type': 'object',
      'properties': {
        'eClass': {
          'type': 'string',
          'default': 'http://www.eclipse.org/emfforms/example/coffeemodel#//ControlUnit'
        },
        'activities': {
          'type': 'array',
          'items': {
            '$ref': '#/definitions/activity'
          }
        },
        'children': {
          'type': 'array',
          'items': {
            'anyOf': [
              { '$ref': '#/definitions/brewingunit'},
              { '$ref': '#/definitions/controlunit'},
              { '$ref': '#/definitions/diptray'},
              { '$ref': '#/definitions/watertank'}
            ]
          }
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
            '$ref': '#/definitions/ram'
          }
        },
        'display': {
          '$ref': '#/definitions/display'
        },
        'userDescription': {
          'type': 'string'
        }
      },
      'additionalProperties': false,
      'required': [
        'processor',
        'dimension',
        'ram'
      ]
    },
    'dimension': {
      '$id': '#dimension',
      'label': 'Dimension',
      'type': 'object',
      'properties': {
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
    'diptray': {
      '$id': '#diptray',
      'label': 'Dip Tray',
      'properties': {
        'eClass': {
          'type': 'string',
          'default': 'http://www.eclipse.org/emfforms/example/coffeemodel#//DipTray'
        },
        'activities': {
          'type': 'array',
          'items': {
            '$ref': '#/definitions/activity'
          }
        },
        'children': {
          'type': 'array',
          'items': {
            'anyOf': [
              { '$ref': '#/definitions/brewingunit'},
              { '$ref': '#/definitions/controlunit'},
              { '$ref': '#/definitions/diptray'},
              { '$ref': '#/definitions/watertank'}
            ]
          }
        }
      },
      'additionalProperties': false
    },
    'display': {
      '$id': '#display',
      'type': 'object',
      'properties': {
        'eClass': {
          'type': 'string',
          'default': 'http://www.eclipse.org/emfforms/example/coffeemodel#//Display'
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
    'processor': {
      '$id': '#processor',
      'type': 'object',
      'properties': {
        'eClass': {
          'type': 'string',
          'default': 'http://www.eclipse.org/emfforms/example/coffeemodel#//Processor'
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
          'type': 'string',
          'enum': [
            'A1T',
            'Z51'
          ]
        },
        'thermalDesignPower': {
          'type': 'integer'
        },
        'manufactoringProcess': {
          'type': 'string',
          'enum': [
            'nm18',
            'nm25'
          ]
        }
      },
      'additionalProperties': false
    },
    'ram': {
      '$id': '#ram',
      'label': 'RAM',
      'type': 'object',
      'properties': {
        'clockSpeed': {
          'type': 'integer'
        },
        'size': {
          'type': 'integer'
        },
        'type': {
          'type': 'string',
          'enum': [
            'SODIMM',
            'SIDIMM'
          ]
        }
      },
      'additionalProperties': false
    },
    'watertank': {
      '$id': '#watertank',
      'label': 'Water Tank',
      'properties': {
        'eClass': {
          'type': 'string',
          'default': 'http://www.eclipse.org/emfforms/example/coffeemodel#//WaterTank'
        },
        'activities': {
          'type': 'array',
          'items': {
            '$ref': '#/definitions/activity'
          }
        },
        'children': {
          'type': 'array',
          'items': {
            'anyOf': [
              { '$ref': '#/definitions/brewingunit'},
              { '$ref': '#/definitions/controlunit'},
              { '$ref': '#/definitions/diptray'},
              { '$ref': '#/definitions/watertank'}
            ]
          }
        }
      },
      'additionalProperties': false
    }
  },
  '$id': '#machine',
  'label': 'Machine',
  'type': 'object',
  'properties': {
    'name': {
      'type': 'string'
    },
    'children': {
      'type': 'array',
      'items': {
        'anyOf': [
          { '$ref': '#/definitions/brewingunit'},
          { '$ref': '#/definitions/controlunit'},
          { '$ref': '#/definitions/diptray'},
          { '$ref': '#/definitions/watertank'}
        ]
      }
    },
    'activities': {
      'type': 'array',
      'items': {
        '$ref': '#/definitions/activity'
      }
    }
  },
  'additionalProperties': false,
  'required': [
    'name'
  ]
};

export const detailSchemata = {
  '#controlunit': controlUnitView,
  '#machine': machineView
};
