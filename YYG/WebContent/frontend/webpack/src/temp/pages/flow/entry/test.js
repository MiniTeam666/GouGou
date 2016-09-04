import React from 'react';
import { render } from 'react-dom';
import  Root from '../login-components/Root';
import injectTapEventPlugin from 'react-tap-event-plugin';
injectTapEventPlugin();
render(
  <Root />,
  document.getElementById('root')
)