import React from 'react';
// import PDF from './PDF.js';
import PDF from 'react-pdf';
import autobind from  'autobind-decorator';

// import './pdf.js';
// import './pdf.worker.js';
@autobind
export default class Pdf extends React.Component {
	constructor(props){
		super(props);

	}
	render(){
		// alert(this.props.pdf);
		 return (
		 	<div stlye={{width:'100%'}}>
		 	<PDF file={this.props.pdf} 
		 			page={1} 
		 			width={300}
		 			onDocumentComplete={this._onDocumentComplete} 
					onPageComplete={this._onPageComplete}  
		 			/>
		 	</div>
		 		);
	}//
	componentDidMount(){
			
	}
	 _onPdfCompleted(page, pages){
	    this.setState({page: page, pages: pages});
	  }
	   _onDocumentCompleted(pages){
	    this.setState({pages: pages});
	  }
  _onPageCompleted(page){
    this.setState({currentPage: page});
  }
}