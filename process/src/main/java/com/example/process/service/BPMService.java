package com.example.process.service;

import com.example.process.model.BPMUser;
import com.example.process.repository.BPMUserRepository;
import org.activiti.engine.*;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
public class BPMService {

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private BPMUserRepository bpmUserRepository;

    @Autowired
    private TaskService taskService;

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private ProcessEngineConfiguration processEngineConfiguration;

    public void startProcess(String assignee) {
       
        repositoryService.createDeployment().addClasspathResource("processes/basic-task-process-bpmn20.xml").deploy();
        BPMUser bpmUser = bpmUserRepository.findByUserName(assignee);
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("bpmUser", bpmUser);
        runtimeService.startProcessInstanceByKey("basic-task-example", variables);
    }

    public void suspendProcess(String processKey) {
        repositoryService.suspendProcessDefinitionById(processKey);
        System.out.println("askıda");
    }

    public void activateProcess(String processId) {
        repositoryService.activateProcessDefinitionById(processId);
        System.out.println("aktif");
    }

    public void startCreditApi(String name, int requestAmount) {
        repositoryService.createDeployment().addClasspathResource("processes/demand-credit-process-bpmn20.xml").deploy();
        Map<String, Object> variables = new HashMap<>();
        variables.put("requestUser", name);
        variables.put("demandAmount", requestAmount);
        runtimeService.startProcessInstanceByKey("demand-credit-process", variables);
    }

    public void startNewProcess() {
        String xmlData = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<definitions xmlns=\"http://www.omg.org/spec/BPMN/20100524/MODEL\" xmlns:bpmndi=\"http://www.omg.org/spec/BPMN/20100524/DI\" xmlns:omgdc=\"http://www.omg.org/spec/DD/20100524/DC\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:activiti=\"http://activiti.org/bpmn\" xmlns:di=\"http://www.omg.org/spec/DD/20100524/DI\" targetNamespace=\"\" xsi:schemaLocation=\"http://www.omg.org/spec/BPMN/20100524/MODEL http://www.omg.org/spec/BPMN/2.0/20100501/BPMN20.xsd\">\n" +
                "  <collaboration id=\"isbirligi\">\n" +
                "    <participant id=\"Katilimcilar\" name=\"&#10;Katılımcılar&#10;\" processRef=\"ImarDurumSureci_1_0\" />\n" +
                "  </collaboration>\n" +
                "  <process id=\"ImarDurumSureci_1_0\" name=\"İmar Durum Süreci\" processType=\"None\" isClosed=\"false\" isExecutable=\"true\" activiti:candidateStarterGroups=\"Kentli Servisi\" activiti:versionTag=\"1.0\">\n" +
                "    <extensionElements />\n" +
                "    <laneSet id=\"kullaniciGruplari\">\n" +
                "      <lane id=\"GelirServisi\" name=\"Gelir Servisi\">\n" +
                "        <flowNodeRef>imarDurumuHarcOdeme</flowNodeRef>\n" +
                "      </lane>\n" +
                "      <lane id=\"ImarSehircilik\" name=\"İmar ve Şehircilik Müdürlüğü\">\n" +
                "        <flowNodeRef>imarDurumuBasvuruFormu</flowNodeRef>\n" +
                "        <flowNodeRef>kararDurumuKontrol</flowNodeRef>\n" +
                "        <flowNodeRef>verilemezSMS</flowNodeRef>\n" +
                "        <flowNodeRef>bitir</flowNodeRef>\n" +
                "        <flowNodeRef>basvuruTipiKontrol</flowNodeRef>\n" +
                "        <flowNodeRef>imarDurumuDosyasi</flowNodeRef>\n" +
                "        <flowNodeRef>imarDurumuSMS</flowNodeRef>\n" +
                "      </lane>\n" +
                "      <lane id=\"KentliServisi\" name=\"Kentli Servisi\">\n" +
                "        <flowNodeRef>basla</flowNodeRef>\n" +
                "      </lane>\n" +
                "      <lane id=\"ebysServisi\" name=\"Ebys Servisi\">\n" +
                "        <flowNodeRef>imarDurumuImza</flowNodeRef>\n" +
                "        <flowNodeRef>imzaBeklemeHavuzu</flowNodeRef>\n" +
                "      </lane>\n" +
                "    </laneSet>\n" +
                "    <userTask id=\"imarDurumuBasvuruFormu\" name=\"Başvuru değerlendirilir\" activiti:formKey=\"com.beyazweb.KYS.modules.imar.forms.FrmImarDurumuBasvuru\" activiti:candidateGroups=\"İmar ve Şehircilik Müdürlüğü\">\n" +
                "      <documentation>Ücretli ise harç hesaplanır, verilemez ise gerekçesi yazılır.</documentation>\n" +
                "      <incoming>Flow_0oj481s</incoming>\n" +
                "      <outgoing>Flow_03h4hwz</outgoing>\n" +
                "    </userTask>\n" +
                "    <exclusiveGateway id=\"kararDurumuKontrol\" name=\"Verilebilir mi?\">\n" +
                "      <incoming>Flow_03h4hwz</incoming>\n" +
                "      <outgoing>flowVerilebilir</outgoing>\n" +
                "      <outgoing>flowVerilemez</outgoing>\n" +
                "    </exclusiveGateway>\n" +
                "    <serviceTask id=\"verilemezSMS\" name=\"Vatandaşa verilemez smsi gönderilir\" activiti:expression=\"${com.beyazweb.KYS.modules.imar.servis.SmsServis.send(&#34;VERILEMEZ&#34;)}\">\n" +
                "      <incoming>flowVerilemez</incoming>\n" +
                "      <outgoing>Flow_1ojf17k</outgoing>\n" +
                "    </serviceTask>\n" +
                "    <endEvent id=\"bitir\" name=\"İşlem bitti&#10;&#10;\">\n" +
                "      <incoming>Flow_150fegb</incoming>\n" +
                "      <incoming>Flow_1ojf17k</incoming>\n" +
                "    </endEvent>\n" +
                "    <exclusiveGateway id=\"basvuruTipiKontrol\" name=\"Ücretli mi?\">\n" +
                "      <incoming>flowVerilebilir</incoming>\n" +
                "      <outgoing>flowUcretli</outgoing>\n" +
                "      <outgoing>flowUcretsiz</outgoing>\n" +
                "    </exclusiveGateway>\n" +
                "    <userTask id=\"imarDurumuHarcOdeme\" name=\"Harç ödenir\" activiti:formKey=\"com.beyazweb.KYS.modules.imar.forms.FrmImarDurumuHarcOdeme\" activiti:candidateGroups=\"Gelir Servisi\">\n" +
                "      <incoming>flowUcretli</incoming>\n" +
                "      <outgoing>Flow_1i19rvk</outgoing>\n" +
                "    </userTask>\n" +
                "    <userTask id=\"imarDurumuDosyasi\" name=\"Düzenlenen imar durumu forma eklenir EBYS servisine gönderilir\" activiti:formKey=\"com.beyazweb.KYS.modules.imar.forms.FrmImarDurumuBasvuru\" activiti:candidateGroups=\"İmar ve Şehircilik Müdürlüğü\">\n" +
                "      <incoming>Flow_1i19rvk</incoming>\n" +
                "      <incoming>flowUcretsiz</incoming>\n" +
                "      <outgoing>Flow_0d764pf</outgoing>\n" +
                "    </userTask>\n" +
                "    <serviceTask id=\"imarDurumuSMS\" name=\"Vatandaşa belgeniz hazır smsi gönderilir\" activiti:expression=\"${com.beyazweb.KYS.modules.imar.servis.SmsServis.send(&#34;BELGEHAZIR&#34;)}\">\n" +
                "      <incoming>Flow_0mrqcmu</incoming>\n" +
                "      <outgoing>Flow_150fegb</outgoing>\n" +
                "    </serviceTask>\n" +
                "    <serviceTask id=\"imarDurumuImza\" name=\"Belge imza için ebys sistemine gönderilir\" activiti:expression=\"${com.beyazweb.KYS.modules.imar.servis.EbysServis.send(basvuruId)}\">\n" +
                "      <incoming>Flow_0d764pf</incoming>\n" +
                "      <outgoing>Flow_0hme3yp</outgoing>\n" +
                "    </serviceTask>\n" +
                "    <task id=\"imzaBeklemeHavuzu\" name=\"İmza işlemleri bekleme havuzu\">\n" +
                "      <incoming>Flow_0hme3yp</incoming>\n" +
                "      <outgoing>Flow_0mrqcmu</outgoing>\n" +
                "    </task>\n" +
                "    <sequenceFlow id=\"flowUcretsiz\" name=\"Hayır\" sourceRef=\"basvuruTipiKontrol\" targetRef=\"imarDurumuDosyasi\">\n" +
                "      <conditionExpression xsi:type=\"tFormalExpression\">${ucretDurumu==\"UCRETSIZ\"}</conditionExpression>\n" +
                "    </sequenceFlow>\n" +
                "    <sequenceFlow id=\"Flow_1ojf17k\" sourceRef=\"verilemezSMS\" targetRef=\"bitir\" />\n" +
                "    <sequenceFlow id=\"flowVerilemez\" name=\"Hayır\" sourceRef=\"kararDurumuKontrol\" targetRef=\"verilemezSMS\">\n" +
                "      <conditionExpression xsi:type=\"tFormalExpression\">${kararDurumu==\"VERILEMEZ\"}</conditionExpression>\n" +
                "    </sequenceFlow>\n" +
                "    <sequenceFlow id=\"Flow_150fegb\" sourceRef=\"imarDurumuSMS\" targetRef=\"bitir\" />\n" +
                "    <sequenceFlow id=\"flowVerilebilir\" name=\"Evet\" sourceRef=\"kararDurumuKontrol\" targetRef=\"basvuruTipiKontrol\">\n" +
                "      <conditionExpression xsi:type=\"tFormalExpression\">${kararDurumu==\"VERILEBILIR\"}</conditionExpression>\n" +
                "    </sequenceFlow>\n" +
                "    <sequenceFlow id=\"Flow_03h4hwz\" sourceRef=\"imarDurumuBasvuruFormu\" targetRef=\"kararDurumuKontrol\" />\n" +
                "    <sequenceFlow id=\"Flow_1i19rvk\" sourceRef=\"imarDurumuHarcOdeme\" targetRef=\"imarDurumuDosyasi\" />\n" +
                "    <sequenceFlow id=\"flowUcretli\" name=\"Evet\" sourceRef=\"basvuruTipiKontrol\" targetRef=\"imarDurumuHarcOdeme\">\n" +
                "      <conditionExpression xsi:type=\"tFormalExpression\">${ucretDurumu==\"UCRETLI\"}</conditionExpression>\n" +
                "    </sequenceFlow>\n" +
                "    <sequenceFlow id=\"Flow_0oj481s\" sourceRef=\"basla\" targetRef=\"imarDurumuBasvuruFormu\" />\n" +
                "    <sequenceFlow id=\"Flow_0d764pf\" sourceRef=\"imarDurumuDosyasi\" targetRef=\"imarDurumuImza\" />\n" +
                "    <sequenceFlow id=\"Flow_0hme3yp\" sourceRef=\"imarDurumuImza\" targetRef=\"imzaBeklemeHavuzu\" />\n" +
                "    <sequenceFlow id=\"Flow_0mrqcmu\" sourceRef=\"imzaBeklemeHavuzu\" targetRef=\"imarDurumuSMS\" />\n" +
                "    <startEvent id=\"basla\" name=\"Başvuru alınır\">\n" +
                "      <outgoing>Flow_0oj481s</outgoing>\n" +
                "    </startEvent>\n" +
                "    <textAnnotation id=\"imzaAciklama\">\n" +
                "      <text>Ebys sistemi imza işlemleri bittiğide imzaReturn(basvuruId) servisini çağırır.  imzaDurumu = \"IMZALANDI\" yapılır. Süreç,  \"Havuz Servisi\" tarafından ilerletilir.</text>\n" +
                "    </textAnnotation>\n" +
                "    <textAnnotation id=\"degerlendirmeAciklamasi\">\n" +
                "      <text>Formda : Verilemez ise gerekçeli yazı hazırlanır, ücretli ise harç hesaplanır</text>\n" +
                "    </textAnnotation>\n" +
                "    <association id=\"Association_1p9fafr\" sourceRef=\"imzaBeklemeHavuzu\" targetRef=\"imzaAciklama\" />\n" +
                "    <association id=\"Association_0ikf7cw\" sourceRef=\"imarDurumuBasvuruFormu\" targetRef=\"degerlendirmeAciklamasi\" />\n" +
                "    <textAnnotation id=\"baslaAciklama\">\n" +
                "      <text>Başvuru, \"Kentli Servisi\" tarafından dilekçe ile veya E-Belediye sitesinden \"Imar Durumu Servisi\" çağırılarak alınır.</text>\n" +
                "    </textAnnotation>\n" +
                "    <association id=\"Association_09fkc4d\" sourceRef=\"basla\" targetRef=\"baslaAciklama\" />\n" +
                "  </process>\n" +
                "  <bpmndi:BPMNDiagram id=\"BPMNDiagram\">\n" +
                "    <bpmndi:BPMNPlane id=\"BPMNPlane_isbirligi\" bpmnElement=\"isbirligi\">\n" +
                "      <bpmndi:BPMNShape id=\"BPMNShape_yeniKatilimci\" bpmnElement=\"Katilimcilar\" isHorizontal=\"true\">\n" +
                "        <omgdc:Bounds x=\"156\" y=\"30\" width=\"1077\" height=\"910\" />\n" +
                "        <bpmndi:BPMNLabel labelStyle=\"BPMNLabelStyleFont12\">\n" +
                "          <omgdc:Bounds x=\"47.49999999999999\" y=\"170.42857360839844\" width=\"12.000000000000014\" height=\"59.142852783203125\" />\n" +
                "        </bpmndi:BPMNLabel>\n" +
                "      </bpmndi:BPMNShape>\n" +
                "      <bpmndi:BPMNShape id=\"Lane_0gdj1nz_di\" bpmnElement=\"GelirServisi\" isHorizontal=\"true\">\n" +
                "        <omgdc:Bounds x=\"186\" y=\"530\" width=\"1047\" height=\"120\" />\n" +
                "      </bpmndi:BPMNShape>\n" +
                "      <bpmndi:BPMNShape id=\"Lane_07wxi0n_di\" bpmnElement=\"ImarSehircilik\" isHorizontal=\"true\">\n" +
                "        <omgdc:Bounds x=\"186\" y=\"250\" width=\"1047\" height=\"280\" />\n" +
                "      </bpmndi:BPMNShape>\n" +
                "      <bpmndi:BPMNShape id=\"BPMNShape_kullaniciGrup\" bpmnElement=\"KentliServisi\" isHorizontal=\"true\">\n" +
                "        <omgdc:Bounds x=\"186\" y=\"30\" width=\"1047\" height=\"220\" />\n" +
                "      </bpmndi:BPMNShape>\n" +
                "      <bpmndi:BPMNShape id=\"Lane_096vvrj_di\" bpmnElement=\"ebysServisi\" isHorizontal=\"true\">\n" +
                "        <omgdc:Bounds x=\"186\" y=\"650\" width=\"1047\" height=\"290\" />\n" +
                "      </bpmndi:BPMNShape>\n" +
                "      <bpmndi:BPMNShape id=\"TextAnnotation_0df9v84_di\" bpmnElement=\"imzaAciklama\">\n" +
                "        <omgdc:Bounds x=\"443\" y=\"787\" width=\"175\" height=\"125\" />\n" +
                "      </bpmndi:BPMNShape>\n" +
                "      <bpmndi:BPMNShape id=\"TextAnnotation_08toqij_di\" bpmnElement=\"degerlendirmeAciklamasi\">\n" +
                "        <omgdc:Bounds x=\"323\" y=\"310\" width=\"135\" height=\"68\" />\n" +
                "      </bpmndi:BPMNShape>\n" +
                "      <bpmndi:BPMNShape id=\"TextAnnotation_0weetk9_di\" bpmnElement=\"baslaAciklama\">\n" +
                "        <omgdc:Bounds x=\"370\" y=\"90\" width=\"99.98924268502583\" height=\"125\" />\n" +
                "      </bpmndi:BPMNShape>\n" +
                "      <bpmndi:BPMNEdge id=\"Flow_0nbxhw3_di\" bpmnElement=\"flowUcretsiz\">\n" +
                "        <di:waypoint x=\"628\" y=\"441\" />\n" +
                "        <di:waypoint x=\"733\" y=\"441\" />\n" +
                "        <bpmndi:BPMNLabel>\n" +
                "          <omgdc:Bounds x=\"667\" y=\"423\" width=\"28\" height=\"14\" />\n" +
                "        </bpmndi:BPMNLabel>\n" +
                "      </bpmndi:BPMNEdge>\n" +
                "      <bpmndi:BPMNEdge id=\"Flow_1ojf17k_di\" bpmnElement=\"Flow_1ojf17k\">\n" +
                "        <di:waypoint x=\"1043\" y=\"330\" />\n" +
                "        <di:waypoint x=\"1083\" y=\"330\" />\n" +
                "        <di:waypoint x=\"1083\" y=\"380\" />\n" +
                "        <di:waypoint x=\"1125\" y=\"380\" />\n" +
                "      </bpmndi:BPMNEdge>\n" +
                "      <bpmndi:BPMNEdge id=\"Flow_1x5ejij_di\" bpmnElement=\"flowVerilemez\">\n" +
                "        <di:waypoint x=\"483\" y=\"416\" />\n" +
                "        <di:waypoint x=\"483\" y=\"330\" />\n" +
                "        <di:waypoint x=\"943\" y=\"330\" />\n" +
                "        <bpmndi:BPMNLabel>\n" +
                "          <omgdc:Bounds x=\"639\" y=\"313\" width=\"28\" height=\"14\" />\n" +
                "        </bpmndi:BPMNLabel>\n" +
                "      </bpmndi:BPMNEdge>\n" +
                "      <bpmndi:BPMNEdge id=\"Flow_150fegb_di\" bpmnElement=\"Flow_150fegb\">\n" +
                "        <di:waypoint x=\"1043\" y=\"441\" />\n" +
                "        <di:waypoint x=\"1083\" y=\"441\" />\n" +
                "        <di:waypoint x=\"1083\" y=\"380\" />\n" +
                "        <di:waypoint x=\"1125\" y=\"380\" />\n" +
                "      </bpmndi:BPMNEdge>\n" +
                "      <bpmndi:BPMNEdge id=\"Flow_0kq264j_di\" bpmnElement=\"flowVerilebilir\">\n" +
                "        <di:waypoint x=\"508\" y=\"441\" />\n" +
                "        <di:waypoint x=\"578\" y=\"441\" />\n" +
                "        <bpmndi:BPMNLabel>\n" +
                "          <omgdc:Bounds x=\"532\" y=\"423\" width=\"23\" height=\"14\" />\n" +
                "        </bpmndi:BPMNLabel>\n" +
                "      </bpmndi:BPMNEdge>\n" +
                "      <bpmndi:BPMNEdge id=\"Flow_03h4hwz_di\" bpmnElement=\"Flow_03h4hwz\">\n" +
                "        <di:waypoint x=\"343\" y=\"441\" />\n" +
                "        <di:waypoint x=\"458\" y=\"441\" />\n" +
                "      </bpmndi:BPMNEdge>\n" +
                "      <bpmndi:BPMNEdge id=\"Flow_1i19rvk_di\" bpmnElement=\"Flow_1i19rvk\">\n" +
                "        <di:waypoint x=\"653\" y=\"590\" />\n" +
                "        <di:waypoint x=\"683\" y=\"590\" />\n" +
                "        <di:waypoint x=\"683\" y=\"441\" />\n" +
                "        <di:waypoint x=\"733\" y=\"441\" />\n" +
                "      </bpmndi:BPMNEdge>\n" +
                "      <bpmndi:BPMNEdge id=\"Flow_1ib7l7j_di\" bpmnElement=\"flowUcretli\">\n" +
                "        <di:waypoint x=\"603\" y=\"466\" />\n" +
                "        <di:waypoint x=\"603\" y=\"550\" />\n" +
                "        <bpmndi:BPMNLabel>\n" +
                "          <omgdc:Bounds x=\"607\" y=\"482\" width=\"23\" height=\"14\" />\n" +
                "        </bpmndi:BPMNLabel>\n" +
                "      </bpmndi:BPMNEdge>\n" +
                "      <bpmndi:BPMNEdge id=\"Flow_0oj481s_di\" bpmnElement=\"Flow_0oj481s\">\n" +
                "        <di:waypoint x=\"293\" y=\"171\" />\n" +
                "        <di:waypoint x=\"293\" y=\"401\" />\n" +
                "      </bpmndi:BPMNEdge>\n" +
                "      <bpmndi:BPMNEdge id=\"Flow_0d764pf_di\" bpmnElement=\"Flow_0d764pf\">\n" +
                "        <di:waypoint x=\"783\" y=\"481\" />\n" +
                "        <di:waypoint x=\"783\" y=\"690\" />\n" +
                "      </bpmndi:BPMNEdge>\n" +
                "      <bpmndi:BPMNEdge id=\"Flow_0hme3yp_di\" bpmnElement=\"Flow_0hme3yp\">\n" +
                "        <di:waypoint x=\"783\" y=\"770\" />\n" +
                "        <di:waypoint x=\"783\" y=\"810\" />\n" +
                "      </bpmndi:BPMNEdge>\n" +
                "      <bpmndi:BPMNEdge id=\"Flow_0mrqcmu_di\" bpmnElement=\"Flow_0mrqcmu\">\n" +
                "        <di:waypoint x=\"833\" y=\"850\" />\n" +
                "        <di:waypoint x=\"993\" y=\"850\" />\n" +
                "        <di:waypoint x=\"993\" y=\"481\" />\n" +
                "      </bpmndi:BPMNEdge>\n" +
                "      <bpmndi:BPMNShape id=\"Activity_1hk7tmj_di\" bpmnElement=\"imarDurumuBasvuruFormu\">\n" +
                "        <omgdc:Bounds x=\"243\" y=\"401\" width=\"100\" height=\"80\" />\n" +
                "      </bpmndi:BPMNShape>\n" +
                "      <bpmndi:BPMNShape id=\"Gateway_1uunjbj_di\" bpmnElement=\"kararDurumuKontrol\" isMarkerVisible=\"true\">\n" +
                "        <omgdc:Bounds x=\"458\" y=\"416\" width=\"50\" height=\"50\" />\n" +
                "        <bpmndi:BPMNLabel>\n" +
                "          <omgdc:Bounds x=\"452\" y=\"473\" width=\"65\" height=\"14\" />\n" +
                "        </bpmndi:BPMNLabel>\n" +
                "      </bpmndi:BPMNShape>\n" +
                "      <bpmndi:BPMNShape id=\"Activity_00hvbce_di\" bpmnElement=\"verilemezSMS\">\n" +
                "        <omgdc:Bounds x=\"943\" y=\"290\" width=\"100\" height=\"80\" />\n" +
                "      </bpmndi:BPMNShape>\n" +
                "      <bpmndi:BPMNShape id=\"Event_0kdughp_di\" bpmnElement=\"bitir\">\n" +
                "        <omgdc:Bounds x=\"1125\" y=\"362\" width=\"36\" height=\"36\" />\n" +
                "        <bpmndi:BPMNLabel>\n" +
                "          <omgdc:Bounds x=\"1121\" y=\"405\" width=\"48\" height=\"40\" />\n" +
                "        </bpmndi:BPMNLabel>\n" +
                "      </bpmndi:BPMNShape>\n" +
                "      <bpmndi:BPMNShape id=\"Gateway_0y89hbw_di\" bpmnElement=\"basvuruTipiKontrol\" isMarkerVisible=\"true\">\n" +
                "        <omgdc:Bounds x=\"578\" y=\"416\" width=\"50\" height=\"50\" />\n" +
                "        <bpmndi:BPMNLabel>\n" +
                "          <omgdc:Bounds x=\"577\" y=\"392\" width=\"52\" height=\"14\" />\n" +
                "        </bpmndi:BPMNLabel>\n" +
                "      </bpmndi:BPMNShape>\n" +
                "      <bpmndi:BPMNShape id=\"Activity_1vfv7hr_di\" bpmnElement=\"imarDurumuHarcOdeme\">\n" +
                "        <omgdc:Bounds x=\"553\" y=\"550\" width=\"100\" height=\"80\" />\n" +
                "      </bpmndi:BPMNShape>\n" +
                "      <bpmndi:BPMNShape id=\"Activity_1v11hid_di\" bpmnElement=\"imarDurumuDosyasi\">\n" +
                "        <omgdc:Bounds x=\"733\" y=\"401\" width=\"100\" height=\"80\" />\n" +
                "      </bpmndi:BPMNShape>\n" +
                "      <bpmndi:BPMNShape id=\"Activity_1s4nd9g_di\" bpmnElement=\"imarDurumuSMS\">\n" +
                "        <omgdc:Bounds x=\"943\" y=\"401\" width=\"100\" height=\"80\" />\n" +
                "      </bpmndi:BPMNShape>\n" +
                "      <bpmndi:BPMNShape id=\"Activity_1oq8mw9_di\" bpmnElement=\"imarDurumuImza\">\n" +
                "        <omgdc:Bounds x=\"733\" y=\"690\" width=\"100\" height=\"80\" />\n" +
                "      </bpmndi:BPMNShape>\n" +
                "      <bpmndi:BPMNShape id=\"Activity_0pkewp1_di\" bpmnElement=\"imzaBeklemeHavuzu\">\n" +
                "        <omgdc:Bounds x=\"733\" y=\"810\" width=\"100\" height=\"80\" />\n" +
                "      </bpmndi:BPMNShape>\n" +
                "      <bpmndi:BPMNShape id=\"BPMNShape_basla\" bpmnElement=\"basla\">\n" +
                "        <omgdc:Bounds x=\"275\" y=\"135\" width=\"36\" height=\"36\" />\n" +
                "        <bpmndi:BPMNLabel>\n" +
                "          <omgdc:Bounds x=\"260\" y=\"113\" width=\"70\" height=\"14\" />\n" +
                "        </bpmndi:BPMNLabel>\n" +
                "      </bpmndi:BPMNShape>\n" +
                "      <bpmndi:BPMNEdge id=\"Association_1p9fafr_di\" bpmnElement=\"Association_1p9fafr\">\n" +
                "        <di:waypoint x=\"733\" y=\"850\" />\n" +
                "        <di:waypoint x=\"618\" y=\"851\" />\n" +
                "      </bpmndi:BPMNEdge>\n" +
                "      <bpmndi:BPMNEdge id=\"Association_0ikf7cw_di\" bpmnElement=\"Association_0ikf7cw\">\n" +
                "        <di:waypoint x=\"293\" y=\"400\" />\n" +
                "        <di:waypoint x=\"333\" y=\"378\" />\n" +
                "      </bpmndi:BPMNEdge>\n" +
                "      <bpmndi:BPMNEdge id=\"Association_09fkc4d_di\" bpmnElement=\"Association_09fkc4d\">\n" +
                "        <di:waypoint x=\"311\" y=\"153\" />\n" +
                "        <di:waypoint x=\"370\" y=\"154\" />\n" +
                "      </bpmndi:BPMNEdge>\n" +
                "    </bpmndi:BPMNPlane>\n" +
                "    <bpmndi:BPMNLabelStyle id=\"BPMNLabelStyleFont11\">\n" +
                "      <omgdc:Font name=\"Arial\" size=\"11\" isBold=\"false\" isItalic=\"false\" isUnderline=\"false\" isStrikeThrough=\"false\" />\n" +
                "    </bpmndi:BPMNLabelStyle>\n" +
                "    <bpmndi:BPMNLabelStyle id=\"BPMNLabelStyleFont12\">\n" +
                "      <omgdc:Font name=\"Arial\" size=\"12\" isBold=\"false\" isItalic=\"false\" isUnderline=\"false\" isStrikeThrough=\"false\" />\n" +
                "    </bpmndi:BPMNLabelStyle>\n" +
                "  </bpmndi:BPMNDiagram>\n" +
                "</definitions>\n";
        System.out.println(xmlData);
        repositoryService.createDeployment().addString("processes/14.bpmn", xmlData).deploy();
        //repositoryService.createDeployment().addClasspathResource("processes/14.bpmn").deploy();
        System.out.println("deploy edildi");
    }


}
