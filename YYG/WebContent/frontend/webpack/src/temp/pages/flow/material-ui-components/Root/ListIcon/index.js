import React from 'react';
import Avatar from 'material-ui/lib/avatar';
import List from 'material-ui/lib/lists/list';
import ListItem from 'material-ui/lib/lists/list-item';
import Divider from 'material-ui/lib/divider';
import Subheader from 'material-ui/lib/Subheader';
import CommunicationChatBubble from 'material-ui/lib/svg-icons/communication/chat-bubble';

const ListExampleChat = () => (
  <div >
    <List>
      <Subheader>Recent chats</Subheader>
      <ListItem
        primaryText="Brendan Lim"
        leftAvatar={<Avatar src="http://www.material-ui.com/v0.15.0-alpha.2/images/ok-128.jpg" />}
        rightIcon={<CommunicationChatBubble />}
      />
      <ListItem
        primaryText="Eric Hoffman"
        leftAvatar={<Avatar src="http://www.material-ui.com/v0.15.0-alpha.2/images/kolage-128.jpg" />}
        rightIcon={<CommunicationChatBubble />}
      />
      <ListItem
        primaryText="Grace Ng"
        leftAvatar={<Avatar src="http://www.material-ui.com/v0.15.0-alpha.2/images/uxceo-128.jpg" />}
        rightIcon={<CommunicationChatBubble />}
      />
      <ListItem
        primaryText="Kerem Suer"
        leftAvatar={<Avatar src="http://www.material-ui.com/v0.15.0-alpha.2/images/kerem-128.jpg" />}
        rightIcon={<CommunicationChatBubble />}
      />
      <ListItem
        primaryText="Raquel Parrado"
        leftAvatar={<Avatar src="http://www.material-ui.com/v0.15.0-alpha.2/images/raquelromanp-128.jpg" />}
        rightIcon={<CommunicationChatBubble />}
      />
    </List>
    <Divider />
    <List>
      <Subheader>Previous chats</Subheader>
      <ListItem
        primaryText="Chelsea Otakan"
        leftAvatar={<Avatar src="http://www.material-ui.com/v0.15.0-alpha.2/images/chexee-128.jpg" />}
      />
      <ListItem
        primaryText="James Anderson"
        leftAvatar={<Avatar src="http://www.material-ui.com/v0.15.0-alpha.2/images/jsa-128.jpg" />}
      />
    </List>
    <Divider />
    <List>
      <Subheader>Previous chats</Subheader>
      <ListItem
        primaryText="Chelsea Otakan"
        leftAvatar={<Avatar src="http://www.material-ui.com/v0.15.0-alpha.2/images/chexee-128.jpg" />}
      />
      <ListItem
        primaryText="James Anderson"
        leftAvatar={<Avatar src="http://www.material-ui.com/v0.15.0-alpha.2/images/jsa-128.jpg" />}
      />
    </List>
  </div>
);

export default ListExampleChat;