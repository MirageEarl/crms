import org.activiti.engine.*;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 工作流测试
 *
 * @author TangBo
 * @create 2017-03-01 11:03
 **/
public class TestActiviti extends TestBase {

    Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    ProcessEngine processEngine;// 获取流程引擎

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private HistoryService historyService;

    @Autowired
    private IdentityService identityService;

    @Test
    public void getProcess() {
        //ProcessEngine processEngine = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("applicationContext-activiti.xml").buildProcessEngine();
        //System.out.println(processEngine);
    }

    /*
        部署流程
     */
    @Test
    public void deploy() {
        // 创建部署环境配置对象
        DeploymentBuilder deploymentBuilder = repositoryService.createDeployment();
        // 部署流程
        Deployment deployment = deploymentBuilder
                .name("测试")//设置部署流程显示名称
                .addClasspathResource("com/vifu/crms/bpm/level.bpmn")//设置流程文件
                .deploy();//部署
        // 打印部署ID
        System.out.println("部署ID为："+deployment.getId());
    }

    /*
        查询流程信息
     */
    @Test
    public void view() {
        List<ProcessDefinition> processDefinitions = repositoryService.createProcessDefinitionQuery()// 创建流程查询对象
//                .processDefinitionId("myProcess_01")// 添加过滤条件
                .processDefinitionKey("myProcess_01")
//                .listPage(0,2);// 分页查询
//                  .orderByDeploymentId().desc()// 添加排序条件
                .list();
        for (ProcessDefinition processDefinition:processDefinitions) {
            System.out.println("流程ID：" + processDefinition.getId());
            System.out.println("流程名称："+processDefinition.getName());
            System.out.println("流程定义的key："+processDefinition.getKey());
            System.out.println("流程定义的version："+processDefinition.getVersion());
            System.out.println("流程定义的deploymentID："+processDefinition.getDeploymentId());
        }
    }

    /*
        删除部署到activiti中的流程定义
     */
    @Test
    public void delDeploy() {
        //  设置需要删除的部署ID
        String deploymentId = "17508";
        //  删除指定的部署信息，如果有关联信息，则报错
//        repositoryService.deleteDeployment(deploymentId);
        //  如果有关联信息，则级联删除
        //  第二个参数cascade,代表是否级联删除
        repositoryService.deleteDeployment(deploymentId,true);
    }


    /**
     * DESCRIPTION:获取流程定义中的资源文件(查看流程图片)
     **/
    @Test
    public void getResource() throws Exception {
        // 获取流程定义下绑定的所有资源文件的名称
        String deploymentID = "8";
        List<String> names = repositoryService.getDeploymentResourceNames(deploymentID);
        String pngName=null;
        for (String name : names) {
            if (name.endsWith(".png")) {
                pngName=name;
            }
        }
        // 获取流程图片
        if (pngName != null) {
            // 拷贝到指定的文件
            File file = new File("G:\\IDEASpace\\crms\\src\\main\\java\\com\\vifu\\crms\\bpm\\pngName.png");
            // 获取文件的输入流
            InputStream in = repositoryService.getResourceAsStream(deploymentID, pngName);
            // 把文件拷贝到指定目录下
            FileUtils.copyInputStreamToFile(in,file);
        }
    }

    /**
     * DESCRIPTION:启动流程
     **/
    @Test
    public void startProcess() throws Exception {
        // 启动流程返回实例对象
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("myProcess_01");
        // 显示实例信息
        System.out.println("流程实例ID：" + processInstance.getId());
        System.out.println("当前活动节点ID"+processInstance.getActivityId());
    }

    /**
     * DESCRIPTION:查询流程任务
     **/
    @Test
    public void queryTask() throws Exception {

        List<Task> taskList = taskService.createTaskQuery().
                processDefinitionKey("myProcess_01").
                list();
        for (Task task : taskList) {
            System.out.println("任务ID:" + task.getId());
            System.out.println("任务名称:" + task.getName());
            System.out.println("任务创建时间:" + task.getCreateTime());
            System.out.println("任务办理者:" + task.getAssignee());
            System.out.println("获取流程执行对象ID:"+task.getExecutionId());
        }
    }

    /**
     * DESCRIPTION:在流程启动时添加流程变量
     **/
    @Test
    public void startFlow() throws Exception {

        String processDefinitionKey="myProcess_01";
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("请假天数","3");
        variables.put("请假原因", "旅游");
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processDefinitionKey, variables);
        System.out.println("pid:"+processInstance.getId());
    }

    /**
     * DESCRIPTION:在任务流转过程中添加任务变量
     **/
    @Test
    public void setVar() throws Exception {

        String taskId = "35005";
        Map<String, Object> variables1 = new HashMap<String, Object>();
        variables1.put("请假时间", "明天");
        // 在办理过程中设置流程变量
        taskService.setVariables(taskId, variables1);// 一次设置多个变量
//        taskService.setVariable(taskId, "name", "variable"); 一次设置一个变量
        // 任务提交时添加流程变量
        Map<String, Object> variables2 = new HashMap<String, Object>();
        variables2.put("备注", "恳请领导批准");
        taskService.complete(taskId, variables2);
    }

    /**
     * DESCRIPTION:在任务流转过程中添加任务变量
     **/
    @Test
    public void setVarByObject() throws Exception {

        String executionId = "25001";
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("value", "流程变量");
        // 在流程实例办理过程中添加流程变量
//        runtimeService.setVariables(executionId,variables);
        // 在流程实例提交时添加流程变量
        runtimeService.signal(executionId,variables);

    }

    /**
     * DESCRIPTION:查询流程历史
     **/
    @Test
    public void queryHistory() throws Exception {

        List<HistoricProcessInstance> historicProcessInstances = historyService.createHistoricProcessInstanceQuery()
                .processDefinitionKey("myProcess_01")
                .list();
        for (HistoricProcessInstance historicProcessInstance : historicProcessInstances) {
            System.out.println("历史ID--" + historicProcessInstance.getId());
            System.out.println("历史名称--" + historicProcessInstance.getName());
            System.out.println("流程ID--" + historicProcessInstance.getProcessDefinitionId());
            System.out.println("开始活动ID--"+historicProcessInstance.getStartActivityId());
            System.out.println("结束活动ID--"+historicProcessInstance.getEndActivityId());
            System.out.println("开始时间--" + historicProcessInstance.getStartTime().toString());
            System.out.println("结束时间--" + historicProcessInstance.getEndTime().toString());
        }
    }

    /**
     * DESCRIPTION:建立用户组
     **/
    @Test
    public void createGroupUser() throws Exception {

        //  创建四个用户测试和开发人员
//        User testUser1 = identityService.newUser("testUser1");
//        User testUser2 = identityService.newUser("testUser2");
//        User developUser1 = identityService.newUser("developUser1");
//        User developUser2 = identityService.newUser("developUser2");

//        identityService.saveUser(testUser1);
//        identityService.saveUser(testUser2);
//        identityService.saveUser(developUser1);
//        identityService.saveUser(developUser2);
        //  创建两个则测试和开发
//        Group testGroup = identityService.newGroup("testGroup");
//        Group developGroup = identityService.newGroup("developGroup");

//        identityService.saveGroup(testGroup)
        //  让用户和用户组建立关系
        identityService.createMembership("testUser1","testGroup");
        identityService.createMembership("testUser2","testGroup");
        identityService.createMembership("developUser1","developGroup");
        identityService.createMembership("developUser2","developGroup");
    }

    /**
     * DESCRIPTION:
     **/
    @Test
    public void setTaskUser() throws Exception {

//        startProcess();// 启动流程
        String groupId = "developGroup";
//        List<Task> myProcess_01 = taskService.createTaskQuery().processDefinitionKey("myProcess_01").list();
        List<Task> myProcess_01 = taskService.createTaskQuery().taskCandidateGroup(groupId).list();
        for (Task task : myProcess_01) {
            System.out.println(task.toString());
        }
//        String taskId = "57504";
//        taskService.addCandidateGroup(taskId,groupId);
//        taskService.
    }
}
