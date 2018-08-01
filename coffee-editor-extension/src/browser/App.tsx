import * as React from 'react';
import { connect } from 'react-redux';
import { TreeWithDetailRenderer } from '@jsonforms/material-tree-renderer';
import { getData, getSchema, getUiSchema } from '@jsonforms/core';
import { createMuiTheme, MuiThemeProvider } from '@material-ui/core/styles';
import * as _ from 'lodash';

const theme = createMuiTheme({
  palette: {
    type: 'dark',
    primary: {
      main: '#FFFFFF'
    },
    secondary: {
      main: '#217DAF',
      dark: '#26A69A'
    },
    background: {
      'default': '#1E1E1E'
    }
  },
  typography: {
    fontSize: 10
  },
  overrides: {
    MuiButton: {
      fab: {
        width: '24px',
        height: '24px',
        minHeight: '0px'
      }
    },
    MuiIconButton: {
      root: {
        minWidth: '0px'
      }
    }
  },
});

interface AppProps {
  uischema: any;
  schema: any;
  rootData: any;
  filterPredicate: any;
  labelProvider: any;
  imageProvider: any;
  saveable: any;
}

class App extends React.Component<AppProps, {}> {

  componentDidUpdate(prevProps) {
    if (!_.isEqual(this.props.rootData, prevProps.rootData)) {
      this.props.saveable.dirty = true;
    }
  }

  render() {
    const { filterPredicate, labelProvider, imageProvider, uischema, schema } = this.props;

    return (
      <MuiThemeProvider theme={theme}>
        <div>
          <TreeWithDetailRenderer
            uischema={uischema}
            schema={schema}
            filterPredicate={filterPredicate}
            labelProvider={labelProvider}
            imageProvider={imageProvider}
          />
        </div>
      </MuiThemeProvider>
    );
  }
}

const mapStateToProps = (state, ownProps) => {
  return {
    uischema: getUiSchema(state),
    schema: getSchema(state),
    filterPredicate: ownProps.filterPredicate,
    labelProvider: ownProps.labelProvider,
    imageProvider: ownProps.imageProvider,
    rootData: getData(state)
  };
};

export default connect(mapStateToProps)(App);
