import { combineReducers, createStore } from 'redux';
import { coffeeSchema, controlUnitView, machineView } from './models/coffee-schema';
import { materialCells, materialRenderers } from '@jsonforms/material-renderers';
import {
  Actions,
  jsonformsReducer,
  JsonFormsState,
  JsonFormsStore
} from '@jsonforms/core';
import * as _ from 'lodash';

export const initStore = async() => {
  const uischema = {
    'type': 'MasterDetailLayout',
    'scope': '#'
  };
  const renderers = materialRenderers;
  const cells = materialCells;
  const jsonforms: JsonFormsState = {
    jsonforms: {
      renderers,
      cells
    }
  };

  const store: JsonFormsStore = createStore(
    combineReducers({
        jsonforms: jsonformsReducer(
        )
      }
    ),
    {
      ...jsonforms
    }
  );

  store.dispatch(Actions.init({}, coffeeSchema, uischema));
  store.dispatch(Actions.registerUISchema((schema => schema['$id']==='#controlunit'?100:-1),controlUnitView));
  store.dispatch(Actions.registerUISchema((schema => schema['$id']==='#machine'?100:-1),machineView));

  return store;
};
