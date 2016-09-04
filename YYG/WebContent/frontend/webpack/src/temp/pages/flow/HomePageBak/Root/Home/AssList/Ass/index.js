import React from 'react';
import Badge from 'material-ui/lib/badge';
import StyleCSS from './index.css';
import Avatar from 'material-ui/lib/avatar';

const BadgeExampleSimple = () => (
  <div className={StyleCSS.icon}>
    <Badge
      badgeContent={4}
      primary={true}
      >
       <Avatar style={{borderRadius:0,marginTop:'-12.5px',marginRight:'-12.5px'}} src="http://wechat.gzmpc.com/kanban_table/icon.jpg" />
    </Badge>
  </div>
);

export default BadgeExampleSimple;