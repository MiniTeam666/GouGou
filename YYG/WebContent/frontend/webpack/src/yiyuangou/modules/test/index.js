import React from 'react';
import Network from  './Network.js';
import { render } from 'react-dom';
import injectTapEventPlugin from 'react-tap-event-plugin';
import EventTest from 'company/util/lib/Event';
import Tabs from './TabsEx';
injectTapEventPlugin();
render(
  <Tabs />,
  document.getElementById('root')
)