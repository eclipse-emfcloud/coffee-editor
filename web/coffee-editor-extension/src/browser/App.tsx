import * as React from 'react';
import { connect } from 'react-redux';
import { TreeWithDetailRenderer } from '@jsonforms/material-tree-renderer';
import {
  TreeEditorProps,
  mapStateToTreeEditorProps
} from 'theia-tree-editor';
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
        minWidth: '0px',
        width: '1em',
        height: '1em'
      }
    },
    MuiSvgIcon: {
      root: {
        fontSize: '18px'
      }
    }
  }
});

class App extends React.Component<TreeEditorProps, {}> {

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


export default connect(mapStateToTreeEditorProps)(App);
