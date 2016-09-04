import React from 'react';
import Tabs from 'material-ui/lib/tabs/tabs';
import Tab from 'material-ui/lib/tabs/tab';
import FontIcon from 'material-ui/lib/font-icon';
import FilterCenterFocus from 'material-ui/lib/svg-icons/image/filter-center-focus';


const TabsExampleIconText = () => (
  <Tabs>
    <Tab
           label={<div><FilterCenterFocus />sdf</div>}

    />
    <Tab
      label={<div><FilterCenterFocus />sdf</div>}
    />
    
  </Tabs>
);

export default TabsExampleIconText;