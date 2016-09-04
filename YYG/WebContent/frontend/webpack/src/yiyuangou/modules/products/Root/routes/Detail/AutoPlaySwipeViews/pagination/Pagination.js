// @flow weak

import React, {Component, PropTypes} from 'react';
import autobind from 'autobind-decorator';

import PaginationDot from './PaginationDot';

const styles = {
  root: {
    display: 'flex',
    flexDirection: 'row',
    marginTop:2,
  },
};
@autobind
export default class Pagination extends Component {
  

  handleClick(event, index){
    this.props.onChangeIndex(index);
  }

  render() {
    const {
      index,
      dots,
    } = this.props;

    const children = [];

    for (let i = 0; i < dots; i++) {
      children.push(
        <PaginationDot
          key={i}
          index={i}
          active={i === index}
          onClick={this.handleClick}
        />
      );
    }

    return (
      <div style={styles.root}>
        {children}
      </div>
    );
  }
}
