import React from 'react';
import { render } from 'react-dom';
import injectTapEventPlugin from 'react-tap-event-plugin';
import Root from './Root';
injectTapEventPlugin();
render(
  <Root />,
  document.getElementById('root')
)