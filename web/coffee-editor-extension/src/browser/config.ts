export const imageProvider = {
  '#activity': 'activity',
  '#brewingunit': 'brewingunit',
  '#controlunit': 'controlunit',
  '#dimension': 'dimension',
  '#diptray': 'diptray',
  '#display': 'display',
  '#machine': 'machine',
  '#processor': 'processor',
  '#ram': 'ram',
  '#watertank': 'watertank'
};

export const labelProvider = {
  '#activity': {
    'constant': 'Activity',
    'property': 'name'
  },
  '#brewingunit': {
    'constant': 'Brewing Unit'
  },
  '#controlunit': {
    'constant': 'Control Unit'
  },
  '#dimension': {
    'constant': 'Dimension'
  },
  '#diptray': {
    'constant': 'Dip Tray'
  },
  '#display': {
    'constant': 'Display'
  },
  '#machine': {
    'constant': 'Machine',
    'property': 'name'
  },
  '#processor': {
    'constant': 'Processor'
  },
  '#ram': {
    'constant': 'RAM'
  },
  '#watertank': {
    'constant': 'Water Tank'
  }
};

export const modelMapping = {
  'attribute': 'eClass',
  'mapping': {
    'http://www.eclipse.org/emfforms/example/coffeemodel#//BrewingUnit': '#brewingunit',
    'http://www.eclipse.org/emfforms/example/coffeemodel#//ControlUnit': '#controlunit',
    'http://www.eclipse.org/emfforms/example/coffeemodel#//DipTray': '#diptray',
    'http://www.eclipse.org/emfforms/example/coffeemodel#//WaterTank': '#watertank'
  }
};
