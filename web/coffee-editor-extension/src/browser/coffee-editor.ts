import { combineReducers, createStore } from 'redux';
import { imageProvider, labelProvider, modelMapping } from './config';
import { coffeeSchema, controlUnitView, machineView } from './models/coffee-schema';
import { materialCells, materialRenderers } from '@jsonforms/material-renderers';
import { filterPredicate } from "theia-tree-editor";
import  {InstanceLabelProvider} from '@jsonforms/material-tree-renderer/lib/helpers/LabelProvider';
import {
  Actions,
  jsonformsReducer,
  JsonSchema7,
  JsonFormsState,
  JsonFormsStore
} from '@jsonforms/core';
import App from './App';
import { defaultProps } from "recompose";
import * as _ from 'lodash';

interface LabelDefinition {
  /** A constant label value displayed for every object for which this label definition applies. */
  constant?: string;
  /** The property name that is used to get a variable part of an object's label. */
  property?: string;
}

const forSchema:InstanceLabelProvider = (schema) => labelProvider[schema["$id"]].constant;
const calculateLabel =
  (schema: JsonSchema7,element: Object): string => {

    if (!_.isEmpty(labelProvider) && labelProvider[schema.$id] !== undefined) {

      if (typeof labelProvider[schema.$id] === 'string') {
        // To be backwards compatible: a simple string is assumed to be a property name
        return element[labelProvider[schema.$id]];
      }
      if (typeof labelProvider[schema.$id] === 'object') {
        const info = labelProvider[schema.$id] as LabelDefinition;
        let label;
        if (info.constant !== undefined) {
          label = info.constant;
        }
        if (!_.isEmpty(info.property) && !_.isEmpty(element[info.property])) {
          label = _.isEmpty(label) ?
            element[info.property] :
            `${label} ${element[info.property]}`;
        }
        if (label !== undefined) {
          return label;
        }
      }
    }

    const namingKeys = Object
      .keys(schema.properties)
      .filter(key => key === '$id' || key === 'name');
    if (namingKeys.length !== 0) {
      return element[namingKeys[0]];
    }

    return JSON.stringify(element);
  };

const imageGetter = (schema: JsonSchema7) =>
  !_.isEmpty(imageProvider) ? `icon ${imageProvider[schema.$id]}` : '';

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

export const CoffeeApp = defaultProps(
  {
    'filterPredicate': filterPredicate(modelMapping),
    'labelProviders': {
      forData:calculateLabel,
      forSchema:forSchema
    },
    'imageProvider': imageGetter
  }
)(App);
