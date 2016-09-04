<div className="container ng-scope" ng-view=""><div className="contact-fullwidth ng-scope">
<!-- ngIf: home.noPhonePersonNumber>0 -->

<div className="alert alert-success text-center ng-hide" role="alert" ng-show="home.enterprisesNumFlag">
	<i className="iconfont text-danger home-alert-iconfont"></i>
	<span className="home-alert-text">上传员工通讯录，即可为每位员工增加最高1000分钟/月（未认证最多300分钟/月）的免费通话时长。点击组织架构右侧“+”，立即添加</span>
</div>
<div className="home main-box clearfix contact-home ng-isolate-scope" ng-click="home.closeIntro()" ng-intro-options="home.IntroOptions" ng-intro-method="CallMe">
	
	<div className="left-box ng-isolate-scope" ng-intro-options="home.IntroOptionsTwo" ng-intro-method="haha">
		<!-- ngIf: home.homeAddContentTag -->
		<div className="panel panel-default panel-inverse panel-bumen" id="step3">
			<div className="panel-heading">
				<h3 className="panel-title">组织架构</h3>
				<span className="home-org-size-content"><!-- ngIf: orgSizeShow.data.authLevel == '5' && orgSizeShow.data.orgLevel && !orgSizeShow.isMaxLevel --></span>
				
				
				
			    
			</div>
			
			<div className="addContact-div">
					<!-- ngIf: home.newCount -->
    				<a href="javascript:void(0)" className="click-btn" ng-click="home.showAddDepModal()"><div className="iconfont"></div><span>添加部门</span></a>
    				<a href="javascript:void(0)" className="click-btn" ng-click="home.addProfile();"><div className="iconfont"></div><span>添加员工</span></a>
    				<a href="#/exceluploadnew" className="click-btn"><div className="iconfont"></div><span>导入/导出员工</span><div className="iconfont iconfont-tuijian"></div></a>
    				<a href="javascript:void(0)" className="click-btn" ng-click="home.showInviteFriends()">
    					<div className="iconfont"></div>
    					<span>团队邀请</span>
    					<!-- ngIf: home.newCount && home.homeAddContentTag -->
    				</a>
		      </div>
			<div left-panel="" className="panel-body ng-isolate-scope" dep-tree-list="home.depTreeList" show-add-dep-modal="home.showAddDepModal" set-dep-dargable-status="home.setDepDargableStatus" save-drag-dep-order="home.saveDragDepOrder" get-dep-dargable-status="home.getDepDargableStatus" get-dep-cache-order="home.getDepCacheOrder" set-dep-cache-order="home.setDepCacheOrder">
	
	<div dep-tree="" set-dep-dargable-status="leftPanel.setDepDargableStatus" save-drag-dep-order="leftPanel.saveDragDepOrder" save-dep-cache-order="leftPanel.saveDepCacheOrder" get-dep-dargable-status="leftPanel.getDepDargableStatus" dep-tree-list="leftPanel.depTreeList" get-dep-cache-order="leftPanel.getDepCacheOrder" set-dep-cache-order="leftPanel.setDepCacheOrder" className="ng-isolate-scope">
	<p className="search-dep-input-box">
		<span className="glyphicon glyphicon-search"></span>
		<span className="vertical-bar"></span>
		<input type="text" placeholder="请输入搜索的部门" className="search-dep-input ng-pristine ng-valid" ng-model="searchDepFilter">
	</p>
	<!-- ngIf: depTree.dragableMark -->

	<script type="text/ng-template" id="nodes_renderer.html">
	  <div ui-tree-handle className="tree-node tree-node-content">
	    <a ng-style="{'color':!node.canManage && node.deptId != -1 ?'#ccc':''}" href="javascript:void(0);" ng-click="depTree.upDateStaff($event)" did="{{node.deptId}}" className="dep-name" ><i ng-if="node.deptId != -1" className="icon-dept iconfont">&#xe613;</i><i ng-if="node.deptId == -1" className="icon-dept iconfont">&#xe627;
</i> {{node.name}} <span className="count" did="{{node.deptId}}">({{node.memberCount}}人)</span></a>
	    <a className="dep-tree-pos-btn" ng-if="node.children && node.children.length > 0" nodrag data-nodrag ng-click="depTree.toggleDep(this)"><span className="iconfont icon-collapsed"><span ng-if="!collapsed">&#xe614;</span><span ng-if="collapsed">&#xe615;</span></span></a>
	  </div>
	  <ol ui-tree-nodes ng-model="node.children" ng-if="!collapsed">
	    <li ng-repeat="node in node.children | filter:searchDepFilter" ui-tree-node ng-include="'nodes_renderer.html'" collapsed="true">
	    </li>
	  </ol>
	</script>
	<div ui-tree="depTree.treeOptions" id="tree2-root" className="tree-root-node ng-scope angular-ui-tree" data-drag-enabled="true" data-drag-delay="40">
	  <ol ui-tree-nodes="" ng-model="depTree.depTreeList" className="ng-scope ng-pristine ng-valid angular-ui-tree-nodes">
	    <!-- ngRepeat: node in depTree.depTreeList --><!-- ngInclude: 'nodes_renderer.html' --><li ng-repeat="node in depTree.depTreeList" ui-tree-node="" ng-include="'nodes_renderer.html'" collapsed="true" className="ng-scope angular-ui-tree-node">
	  <div ui-tree-handle="" className="tree-node tree-node-content ng-scope angular-ui-tree-handle">
	    <a ng-style="{'color':!node.canManage &amp;&amp; node.deptId != -1 ?'#ccc':''}" href="javascript:void(0);" ng-click="depTree.upDateStaff($event)" did="-1" className="dep-name ng-binding dep-name-hl"><!-- ngIf: node.deptId != -1 --><!-- ngIf: node.deptId == -1 --><i ng-if="node.deptId == -1" className="icon-dept iconfont ng-scope">
</i><!-- end ngIf: node.deptId == -1 --> Guang <span className="count ng-binding" did="-1">(2人)</span></a>
	    <!-- ngIf: node.children && node.children.length > 0 --><a className="dep-tree-pos-btn ng-scope" ng-if="node.children &amp;&amp; node.children.length > 0" nodrag="" data-nodrag="" ng-click="depTree.toggleDep(this)"><span className="iconfont icon-collapsed"><!-- ngIf: !collapsed --><!-- ngIf: collapsed --><span ng-if="collapsed" className="ng-scope"></span><!-- end ngIf: collapsed --></span></a><!-- end ngIf: node.children && node.children.length > 0 -->
	  </div>
	  <!-- ngIf: !collapsed -->
	</li><!-- end ngRepeat: node in depTree.depTreeList -->
	  </ol>
	</div>

</div>
</div>
		</div>
	</div>
	
	<div className="right-box panel ng-isolate-scope" right-box="" current-dep-info="home.currentDepInfo" show-edit-dep-modal="home.showEditDepModal" update-member-count="home.updateMemberCount">
	<div className="right-box-header panel-heading">
        <h3 className="dep-title panel-title ng-binding">Guang</h3>
		<!-- ngIf: rightBox.createDeptGroup -->
        <!-- ngIf: rightBox.deptHiding -->
        <!-- ngIf: rightBox.isOuterDept -->
		<!-- ngIf: rightBox.deptId && rightBox.canManage --><button id="step4" className="btn btn-default edit-dep-btn btn-xs ng-scope" ng-if="rightBox.deptId &amp;&amp; rightBox.canManage" ng-click="rightBox.showEditDepModal()">部门编辑</button><!-- end ngIf: rightBox.deptId && rightBox.canManage -->
		<div className="staff-search contact-search ng-scope" ng-controller="SearchStaff as SS">
            <form ng-submit="SS.onSubmit()" className="ng-pristine ng-valid">
                <div className="input-group">
                    <div className="ddadmin-autocomplete clearfix " id="" attr-input-id="staff-search-input" ddadmin-autocomplete="" attr-placeholder="搜索员工" ng-model="SS.input" on-pre-select="SS.onPreSelect" on-type="SS.onType" on-select="SS.onSelect" attr-input-className="form-control search-input" data="SS.source" type="text">          <input type="text" ng-model="searchParam" placeholder="搜索员工" className="form-control search-input" id="staff-search-input">          <ul ng-show="completing" className="ng-hide">            <!-- ngRepeat: suggestion in suggestions  | orderBy:'suggestion.value.toString()' track by $index -->          </ul>        </div>
                        <span className="input-group-btn">
                            <button className="btn btn-primary" type="submit"><span className="glyphicon glyphicon-search"></span></button>
                        </span>
                </div>
            </form>
        </div>
	</div>
	<!-- ngIf: rightBox.canManage --><div id="step6" ng-if="rightBox.canManage" className="ng-scope">
		<!-- ngIf: rightBoxNavbar.currentDepParents.length != 0 -->
		<!-- ngIf: childrenDep.childrenDepList.length && childrenDep.deptId != -1 -->
		<div className="staff-box ng-isolate-scope" staff="" dep-staff="rightBox.depStaff" update-member-count="rightBox.updateMemberCount" dep-boss="rightBox.depBoss">
	<h4 className="staff-title pull-left"><i className="iconfont"></i>部门人员</h4>
	<button type="button" className="btn btn-primary btn-sm pull-right" style="margin-top:15px;margin-right: 16px" ng-click="staff.addPerson()">
		添加员工
	</button>
	<!-- ngIf: staff.deleteShowTag || staff.sortShowTag -->
	<table className="table table-bordered staff-table">
		<thead>
			<tr>
				<th>
					<div className="dep-staff-name">
						<input type="checkbox" className="del-staff-checkbox-all ng-pristine ng-valid" ng-model="staff.checkAll" ng-change="staff.selectAll()">
						<span>姓名</span>
					</div>
				</th>
				<th>
					<span>员工UserID&nbsp;<i className="iconfont profile-iconfont" tooltip="员工在企业的唯一标识，用于钉钉开放平台和excel表格中，该字段可初始设定或自动生成，且不可修改
"></i></span>
				</th>
				<!-- ngIf: staff.emailDomainName -->
				<th className="dep-staff-position">
					<span>职位</span>
				</th>
				<th>
					<span>工号</span>
				</th>
				<th>
					<span>手机号</span>
				</th>
				<th className="dep-staff-do">
					<span>操作</span>
				</th>
			</tr>
		</thead>
		<tbody ui-sortable="staff.sortableOptions" ng-model="staff.depStaff" className="ng-pristine ng-valid ui-sortable">
			<!-- ngRepeat: item in staff.depStaff --><tr ng-repeat="item in staff.depStaff" className="ng-scope ui-sortable-handle">
				<td>
					<div className="checkbox-td">
						<input type="checkbox" className="del-staff-checkbox ng-pristine ng-valid" uid="8244249" ng-model="item.isChecked" ng-change="staff.showDelete()">
						<span className="staff-name-link ng-binding">黄俊东<!-- ngIf: item.orgEmail --><span className="label label-success staff-boss-label ng-hide" ng-show="item.isManage">主管</span></span>
					</div>
					
				</td>
				<td className="ng-binding">manager2983</td>
				<!-- ngIf: staff.emailDomainName -->
				<td className="ng-binding"></td>

				<td className="ng-binding">manager2983</td>
				<td>
					<span className="staff-last-info ng-binding"><!-- ngIf: item.stateCode && item.stateCode != '86' -->18825180170</span>
				</td>
				<td>
					<span className="home-staff-do-a" ng-click="staff.showProfile(item.uid);">编辑</span>
					<a href="javascript:void(0)" ng-click="staff.removeUser(item.uid)" tooltip="如果一个人属于多部门，则只将该人从本部门移除" className="home-staff-do-a home-staff-del-a">删除</a>
				</td>
			</tr><!-- end ngRepeat: item in staff.depStaff -->
		</tbody>
	</table>
</div>
	</div><!-- end ngIf: rightBox.canManage -->
    <!-- ngIf: !rightBox.canManage -->
</div>

</div>

<div id="modal-list">
	
	
</div>
<div className="Intro-btn">
	<div className="intro-btn-content" ng-click="haha()"></div>
</div>

<a className="ad-link ad-help" href="https://static.dingtalk.com/media/lBDOAOMh6s5T-KOdzlgICKU.pdf?lwfrom=20150813151101146" target="_blank"><img src="//i01.lw.aliimg.com/media/lALOACk_XldF_69_87.png"></a>
</div></div>