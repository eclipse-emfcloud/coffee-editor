import * as React from 'react';
import { connect } from 'react-redux';
import  TreeWithDetailRenderer from '@jsonforms/material-tree-renderer/lib/tree/TreeWithDetailRenderer';
import {
  DIRTY_CLASS,
  TreeEditorProps,
  mapStateToTreeEditorProps
} from 'theia-tree-editor';
import { createMuiTheme, MuiThemeProvider } from '@material-ui/core/styles';
import * as _ from 'lodash';
import { JsonFormsReduxContext } from '@jsonforms/react';

const theme = createMuiTheme({
  palette: {
    type: 'light',
    primary: {
      main: '#616161'
    },
    secondary: {
      main: '#d7eaf8',
      dark: '#26A69A'
    },
    background: {
      'default': '#1E1E1E'
    }
  },
  typography: {
    fontSize: 13,
    fontFamily: '"Helvetica Neue"'
  },
  overrides: {
    MuiButton: {
      // fab: {
      //   width: '24px',
      //   height: '24px',
      //   minHeight: '0px'
      // }
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
    },
    MuiTypography: {
      root: {
        color: '#616161'
      },
      body1: {
        color: '#616161'
      },
      // subheading: {
      //   color: '#616161'
      // },
      // title: {
      //   color: '#616161'
      // }
    }
  }
});

class App extends React.Component<TreeEditorProps, {}> {

  componentDidUpdate(prevProps) {
    const dirtyClass = ` ${DIRTY_CLASS}`;
    if (!_.isEqual(this.props.rootData, prevProps.rootData)) {
      this.props.widget.saveable.dirty = true;
      if (!this.props.widget.title.className.includes(dirtyClass)) {
        this.props.widget.title.className += dirtyClass;
      }
    }
  }

  render() {
    const { filterPredicate, labelProviders, imageProvider, uischema, schema } = this.props;

    return (
      <MuiThemeProvider theme={theme}>
        <div>
        <JsonFormsReduxContext>
          <TreeWithDetailRenderer
            uischema={uischema}
            schema={schema}
            filterPredicate={filterPredicate}
            labelProviders={labelProviders}
            imageProvider={imageProvider}
          />
          </JsonFormsReduxContext>
        </div>
      </MuiThemeProvider>
    );
  }
}


export default connect(mapStateToTreeEditorProps)(App);
