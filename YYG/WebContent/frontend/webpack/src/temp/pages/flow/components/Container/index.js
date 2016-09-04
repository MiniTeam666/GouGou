import React,{Component} from 'react';
import styles from './index.css';
import Leftpart from '../Leftpart';
export default class Container extends Component{
  
  constructor(props) {
    super(props);
  }

  render(){
    return (
        <div className={styles.container}>
          <Leftpart />

        </div>
    );
  }

  componentDidUpdate( prevProps,  prevState){

  }

}  