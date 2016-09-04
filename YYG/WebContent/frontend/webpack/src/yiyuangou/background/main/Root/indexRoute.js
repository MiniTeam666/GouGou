import account from './routes/Account/indexRoute';
import product from './routes/Product/indexRoute';
import order from './routes/Order/indexRoute';
export default {
  childs:[
	  product,
	  account,
	  order,
  ]
}