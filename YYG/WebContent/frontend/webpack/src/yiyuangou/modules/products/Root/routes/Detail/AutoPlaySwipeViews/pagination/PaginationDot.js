// @flow weak

import React, {Component, PropTypes} from 'react';
import autobind from 'autobind-decorator';

const styles = {
  root: {
    height: 18,
    width: 12,
    cursor: 'pointer',
  },
  dot: {
    backgroundColor: '#e4e6e7',
    height: 8,
    width: 8,
    borderRadius: 4,
    margin: 4,
  },
  active: {
    backgroundColor: '#f97b7b',
  },
};
@autobind
export default class PaginationDot extends Component {
  // static propTypes = {
  //   active: PropTypes.bool.isRequired,
  //   index: PropTypes.number.isRequired,
  //   onClick: PropTypes.func.isRequired,
  // };

  handleClick (event)  {
    this.props.onClick(event, this.props.index);
  }

  render() {
    const {
      active,
    } = this.props;

    let styleDot;

    if (active) {
      styleDot = Object.assign({}, styles.dot, styles.active);
    } else {
      styleDot = styles.dot;
    }

    return (
      <div style={styles.root} onClick={this.handleClick}>
        <div style={styleDot} />
      </div>
    );
  }
}
