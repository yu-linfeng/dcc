<template>
    <section>
        <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
            <el-form :inline="true" :model="filters">
                <el-form-item>
                    <el-button type="success" @click="viewNodePropertyItem">查看</el-button>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="addNode">新增</el-button>
                </el-form-item>
                <el-form-item>
                    <el-button type="info" @click="editNode">编辑</el-button>
                </el-form-item>
                <el-form-item>
                    <el-button type="danger" @click="handleDel">删除</el-button>
                </el-form-item>
            </el-form>
        </el-col>

        <el-table :data="properties" highlight-current-row v-loading="listLoading" @selection-change="selsChange" style="width: 100%;">
            <el-table-column type="selection" width="55">
            </el-table-column>
            <el-table-column type="index" width="70" label="序号">
            </el-table-column>
            <el-table-column prop="name" width="300" label="属性名称">
            </el-table-column>
            <el-table-column prop="desc" label="说明">
            </el-table-column>

        </el-table>

        <!--新增界面-->
        <el-dialog title="新增" v-model="addFormVisible" :close-on-click-modal="false" width="20%">
            <el-form :model="addForm" label-width="80px" :rules="addFormRules" ref="addForm">
                <el-form-item label="配置名称" prop="itemName">
                    <el-input v-model="addForm.itemName" auto-complete="off"></el-input>
                </el-form-item>
                <el-form-item label="配置类型">
                    <el-select v-model="propertyValueType" placeholder="请选择">
                        <el-option  v-for="item in options" :key="item.value" :label="item.label" :value="item.value">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="配置值" prop="itemValue">
                    <el-input type="textarea" :autosize="{ minRows: 5, maxRows: 10}" v-model="addForm.itemValue" auto-complete="off"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click.native="addFormVisible = false">取消</el-button>
                <el-button type="primary" @click.native="addSubmit" :loading="addLoading">提交</el-button>
            </div>
        </el-dialog>

        <!--编辑界面-->
        <el-dialog title="编辑" v-model="editFormVisible" :close-on-click-modal="false" width="20%">
            <el-form :model="editForm" label-width="80px" :rules="editFormRules" ref="addForm">
                <el-form-item label="配置名称" prop="itemName">
                    <el-input v-model="editForm.itemName" auto-complete="off"></el-input>
                </el-form-item>
                <el-form-item label="配置类型">
                    <el-select v-model="propertyValueType" placeholder="请选择">
                        <el-option  v-for="item in options" :key="item.value" :label="item.label" :value="item.value">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="配置值" prop="itemValue">
                    <el-input type="textarea" :autosize="{ minRows: 5, maxRows: 10}" v-model="editForm.itemValue" auto-complete="off"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click.native="editFormVisible = false">取消</el-button>
                <el-button type="primary" @click.native="editSubmit" :loading="addLoading">提交</el-button>
            </div>
        </el-dialog>

        <!--查看界面-->
        <el-dialog title="查看" v-model="viewFormVisible" :close-on-click-modal="false" width="20%">
            <el-form :model="viewForm" label-width="80px" ref="viewForm">
                <el-form-item label="配置名称" prop="itemName">
                    <el-input v-model="viewForm.itemName" auto-complete="off"></el-input>
                </el-form-item>
                <el-form-item label="配置类型">
                    <el-select v-model="propertyValueType" placeholder="请选择">
                        <el-option  v-for="item in options" :key="item.value" :label="item.label" :value="item.value">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="配置值" prop="itemValue">
                    <el-input type="textarea" :autosize="{ minRows: 5, maxRows: 10}" v-model="viewForm.itemValue" auto-complete="off"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click.native="viewFormVisible = false">关闭</el-button>
            </div>
        </el-dialog>
    </section>
</template>

<script>
    import axios from 'axios';
    import fecha from 'fecha';
    import util from '../../common/js/util'
    //import { getUserListPage, removeUser, batchRemoveUser, editUser, addUser, queryPath } from '../../api/api';

    export default {
        data() {
            return {
                options: [
                    {
                        value: 'json',
                        label: 'json'
                    }
                ],
                propertyValueType: 'json',
                filters: {
                    path: ''
                },
                configs: [],
                properties:[],
                total: 0,
                page: 1,
                listLoading: false,
                sels: [],//列表选中列
                
                /**
                 * 新增窗口
                 */
                addForm: {
                    itemName: '',
                    itemValue: ''
                },
                addFormVisible: false,
                addLoading: false,
                addFormRules: {
                    itemName: [
                       { required: true, message: '请输入配置名称', trigger: 'blur' } 
                    ],
                    itemValue: [
                       { required: true, message: '请输入配置值', trigger: 'blur' } 
                    ]
                },
                /**
                 * 编辑窗口
                 */
                editForm: {
                    itemName: '',
                    itemValue: ''
                },
                editFormVisible: false,
                editLoading: false,
                editFormRules: {
                    itemName: [
                       { required: true, message: '请输入配置名称', trigger: 'blur' } 
                    ],
                    itemValue: [
                       { required: true, message: '请输入配置值', trigger: 'blur' } 
                    ]
                },

                /**
                 * 查看
                 */
                viewFormVisible:false,
                viewForm: {
                    itemName: '',
                    itemValue: ''
                }
                
            }
        },
        methods: {
            handleCurrentChange(val) {
                this.page = val;
                this.getNodeProperty();
            },
            /**
             * 获取节点配置信息
             */
            getNodeProperty() {
                let path = this.$route.params.path;
                this.listLoading = true;
                axios.post(`http://localhost:8080/property/getProperty`, {
                    path : path
                }).then((response) => {
                    this.properties = response.data.data.properties;
                    if (this.properties === undefined) {
                        this.properties = [];
                    }
                    this.listLoading = false;
                })
            },
            //删除
            handleDel: function () {
                if (this.sels.length !== 1) {
                    this.$message({
                        message: '删除失败，最多只能删除一个Path',
                        type: 'error'
                    });
                    return ;
                }
                var node = this.sels[0];

                this.$confirm('确认删除该记录吗?', '提示', {
                }).then(() => {

                    this.listLoading = true;

                    axios.post(`http://localhost:8080/node/deleteNode`, {
                        path : node.path
                    }).then((response) => {
                        this.listLoading = false;
                        this.$message({
                            message: '删除成功',
                            type: 'success'
                        });
                        this.getNodeProperty();
                    });

                }).catch(() => {

                });
            },
            addNode: function () {
                this.addFormVisible = true;
                this.addForm = {
                    itemName: '',
                    itemValue: ''
                };
            },
            editNode: function () {
                this.editFormVisible = true;
                let itemValueJson = {};
                for (let i = 0; i < this.sels[0].configs.length; i++) {
                    itemValueJson[this.sels[0].configs[i].name] = this.sels[0].configs[i].value;
                }
                this.editForm = {
                    itemName: this.sels[0].name,
                    itemValue: JSON.stringify(itemValueJson)
                };
            },
            //新增
            addSubmit: function () {
                this.$refs.addForm.validate((valid) => {
                    if (valid) {
                        this.$confirm('确认提交吗？', '提示', {}).then(() => {
                            //this.addLoading = true;
                            debugger;
                            let configObject = JSON.parse(this.addForm.itemValue);
                            let configs = [];
                            for (let key in configObject) {
                                let config = {
                                    name : key,
                                    value : configObject[key]
                                };
                                configs.push(config)
                            }
                            let property = {
                                "name": this.addForm.itemName,
                                "configs":configs
                            };

                            let node = {};
                            node.path = this.$route.params.path;
                            this.properties.push(property);
                            node.properties = this.properties;

                            console.info(node);
                            axios.post(`http://localhost:8080/property/setProperty`, {
                                path: node.path,
                                properties: node.properties
                            }).then((response) => {
                                this.addLoading = false;
                                this.$message({
                                    message: '提交成功',
                                    type: 'success'
                                });
                                this.$refs['addForm'].resetFields();
                                this.addFormVisible = false;
                                this.getNodeProperty();
                            });
                        });
                    }
                });
            },
            editSubmit: function () {
                this.$confirm('确认提交吗？', '提示', {}).then(() => {
                    //this.addLoading = true;
                    let configObject = JSON.parse(this.editForm.itemValue);
                    let configs = [];
                    for (let key in configObject) {
                        let config = {
                            name : key,
                            value : configObject[key]
                        };
                        configs.push(config)
                    }
                    let property = {
                        "name": this.editForm.itemName,
                        "configs":configs
                    };

                    let node = {};
                    node.path = this.$route.params.path;
                    this.properties.push(property);
                    node.properties = this.properties;

                    console.info(node);
                    axios.post(`http://localhost:8080/property/setProperty`, {
                        path: node.path,
                        properties: node.properties
                    }).then((response) => {
                        this.addLoading = false;
                        this.$message({
                            message: '提交成功',
                            type: 'success'
                        });
                        this.$refs['editForm'].resetFields();
                        this.editFormVisible = false;
                        this.getNodeProperty();
                    });
                });
            },
            selsChange: function (sels) {
                this.sels = sels;
            },
            viewNodePropertyItem: function () {
                let row = this.sels[0];
                this.viewFormVisible = true;
                let itemValueJson = {};
                for (let i = 0; i < row.configs.length; i++) {
                    itemValueJson[row.configs[i].name] = row.configs[i].value;
                }
                this.viewForm = {
                    itemName: row.name,
                    itemValue: JSON.stringify(itemValueJson)
                };
            }
        },
        mounted() {
            this.getNodeProperty();
        }
    }

</script>

<style scoped>

</style>