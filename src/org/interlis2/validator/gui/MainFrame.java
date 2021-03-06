package org.interlis2.validator.gui;

import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ch.ehi.basics.logging.EhiLogger;
import ch.ehi.basics.view.*;
import ch.ehi.basics.settings.Settings;
import ch.ehi.basics.swing.SwingWorker;
import ch.ehi.basics.tools.StringUtility;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.interlis2.validator.Main;
import org.interlis2.validator.Validator;

/** GUI of ilivalidator.
 */
public class MainFrame extends JFrame {
	private java.util.ResourceBundle rsrc=java.util.ResourceBundle.getBundle("org.interlis2.validator.gui.IliValidatorTexts");
	private Settings settings=null;
	private javax.swing.JPanel jContentPane = null;

	private javax.swing.JLabel xtfFileLabel = null;
	// area to display one file per line in multiple input file validation
	private javax.swing.JTextArea xtfFileUi = null;
	private javax.swing.JCheckBox allObjectsAccessibleUi = null;
	private javax.swing.JButton doXtfFileSelBtn = null;
	
	private javax.swing.JLabel modelNamesLabel = null;
	private javax.swing.JTextField modelNamesUi = null;
	
	private javax.swing.JLabel configFileLabel = null;
	private javax.swing.JTextField configFileUi = null;
	private javax.swing.JButton doConfigFileSelBtn = null;
	
	private javax.swing.JLabel logFileLabel = null;
	private javax.swing.JTextField logFileUi = null;
	private javax.swing.JButton doLogFileSelBtn = null;
	
	private javax.swing.JLabel xtfLogFileLabel = null;
	private javax.swing.JTextField xtfLogFileUi = null;
	private javax.swing.JButton doXtfLogFileSelBtn = null;
	
	private javax.swing.JTextArea logUi = null;
	private javax.swing.JButton clearlogBtn = null;
	
	
	public MainFrame() {
		super();
		initialize();
	}
	private void initialize() {
		this.setSize(500, 361);
		this.setContentPane(getJContentPane());
		this.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
		this.setName(Main.APP_NAME);
		this.setTitle(rsrc.getString("MainFrame.Title"));
		
		//creates a border, which looks like jtextfield border
		xtfFileUi.setBorder(new JTextField().getBorder());
		
	    //Create the menu bar.
		JMenuBar menuBar = new JMenuBar();
	    setJMenuBar(menuBar);

	    JMenu menu = new JMenu(rsrc.getString("MainFrame.ToolsMenu"));
	    menu.setMnemonic(KeyEvent.VK_T);
	    menuBar.add(menu);
	    JMenuItem menuItem = new JMenuItem(rsrc.getString("MainFrame.ModelRepositoriesMenuItem"));
		menuItem.addActionListener(new ActionListener(){
			public void actionPerformed(java.awt.event.ActionEvent e){
				ch.interlis.ili2c.gui.RepositoriesDialog dlg=new ch.interlis.ili2c.gui.RepositoriesDialog(MainFrame.this);
				dlg.setIlidirs(settings.getValue(Validator.SETTING_ILIDIRS));
				dlg.setHttpProxyHost(settings.getValue(ch.interlis.ili2c.gui.UserSettings.HTTP_PROXY_HOST));
				dlg.setHttpProxyPort(settings.getValue(ch.interlis.ili2c.gui.UserSettings.HTTP_PROXY_PORT));
				if(dlg.showDialog()==ch.interlis.ili2c.gui.RepositoriesDialog.OK_OPTION){
					String ilidirs=dlg.getIlidirs();
					if(ilidirs==null){
						ilidirs=Validator.SETTING_DEFAULT_ILIDIRS;
					}
					settings.setValue(Validator.SETTING_ILIDIRS,ilidirs);
					settings.setValue(ch.interlis.ili2c.gui.UserSettings.HTTP_PROXY_HOST,dlg.getHttpProxyHost());
					settings.setValue(ch.interlis.ili2c.gui.UserSettings.HTTP_PROXY_PORT,dlg.getHttpProxyPort());
					saveSettings(settings);
				}
			}

		});
	    menu.add(menuItem);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
              saveSettings(getSettings());
  	    	System.exit(0);
            }
          });
	    
	}
	private void saveSettings(Settings settings) {
        // save not all, but only some values 
		Settings toSave=new Settings();
		toSave.setValue(ch.interlis.ili2c.gui.UserSettings.WORKING_DIRECTORY,settings.getValue(ch.interlis.ili2c.gui.UserSettings.WORKING_DIRECTORY));
		toSave.setValue(Validator.SETTING_ILIDIRS,settings.getValue(Validator.SETTING_ILIDIRS));
		toSave.setValue(Validator.SETTING_MODELNAMES,settings.getValue(Validator.SETTING_MODELNAMES));
		toSave.setValue(Validator.SETTING_ALL_OBJECTS_ACCESSIBLE,settings.getValue(Validator.SETTING_ALL_OBJECTS_ACCESSIBLE));
		toSave.setValue(ch.interlis.ili2c.gui.UserSettings.HTTP_PROXY_HOST,settings.getValue(ch.interlis.ili2c.gui.UserSettings.HTTP_PROXY_HOST));
		toSave.setValue(ch.interlis.ili2c.gui.UserSettings.HTTP_PROXY_PORT,settings.getValue(ch.interlis.ili2c.gui.UserSettings.HTTP_PROXY_PORT));
		Main.writeSettings(toSave);
	}
	private javax.swing.JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new javax.swing.JPanel();
			java.awt.GridBagConstraints xtfFileLabelConstraints = new java.awt.GridBagConstraints();
			java.awt.GridBagConstraints xtfFileUiConstraints = new java.awt.GridBagConstraints();
			java.awt.GridBagConstraints allObjectsAccessibleConstraints = new java.awt.GridBagConstraints();
			java.awt.GridBagConstraints doXtfFileSelBtnConstraints = new java.awt.GridBagConstraints();
			
			java.awt.GridBagConstraints modelNamesLabelConstraints = new java.awt.GridBagConstraints();
			java.awt.GridBagConstraints modelNamesUiConstraints = new java.awt.GridBagConstraints();

			java.awt.GridBagConstraints logFileLabelConstraints = new java.awt.GridBagConstraints();
			java.awt.GridBagConstraints logFileUiConstraints = new java.awt.GridBagConstraints();
			java.awt.GridBagConstraints doLogFileSelBtnConstraints = new java.awt.GridBagConstraints();
			
			java.awt.GridBagConstraints xtfLogFileLabelConstraints = new java.awt.GridBagConstraints();
			java.awt.GridBagConstraints xtfLogFileUiConstraints = new java.awt.GridBagConstraints();
			java.awt.GridBagConstraints doXtfLogFileSelBtnConstraints = new java.awt.GridBagConstraints();
			
			java.awt.GridBagConstraints configFileLabelConstraints = new java.awt.GridBagConstraints();
			java.awt.GridBagConstraints configFileUiConstraints = new java.awt.GridBagConstraints();
			java.awt.GridBagConstraints doConfigFileSelBtnConstraints = new java.awt.GridBagConstraints();
			
			java.awt.GridBagConstraints clearlogBtnConstraints = new java.awt.GridBagConstraints();
			java.awt.GridBagConstraints logPaneConstraints = new java.awt.GridBagConstraints();
			java.awt.GridBagConstraints doValidateConstraints = new java.awt.GridBagConstraints();
			
			// row 0
			xtfFileLabelConstraints.gridx = 0;
			xtfFileLabelConstraints.gridy = 0;
			xtfFileLabelConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
			xtfFileUiConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
			xtfFileUiConstraints.weightx = 1.0;
			xtfFileUiConstraints.gridx = 1;
			xtfFileUiConstraints.gridy = 0;
			xtfFileUiConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
			doXtfFileSelBtnConstraints.gridx = 2;
			doXtfFileSelBtnConstraints.gridy = 0;
			
			// row 1
			allObjectsAccessibleConstraints.gridx = 1;
			allObjectsAccessibleConstraints.gridy = 1;
			allObjectsAccessibleConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
			
			// row 2
			modelNamesLabelConstraints.gridx = 0;
			modelNamesLabelConstraints.gridy = 2;
			modelNamesLabelConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
			modelNamesUiConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
			modelNamesUiConstraints.weightx = 1.0;
			modelNamesUiConstraints.gridx = 1;
			modelNamesUiConstraints.gridy = 2;
			modelNamesUiConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
			
			// row 3
			logFileLabelConstraints.gridx = 0;
			logFileLabelConstraints.gridy = 3;
			logFileLabelConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
			logFileUiConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
			logFileUiConstraints.weightx = 1.0;
			logFileUiConstraints.gridx = 1;
			logFileUiConstraints.gridy = 3;
			logFileUiConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
			doLogFileSelBtnConstraints.gridx = 2;
			doLogFileSelBtnConstraints.gridy = 3;
			
			// row 4
			xtfLogFileLabelConstraints.gridx = 0;
			xtfLogFileLabelConstraints.gridy = 4;
			xtfLogFileLabelConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
			xtfLogFileUiConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
			xtfLogFileUiConstraints.weightx = 1.0;
			xtfLogFileUiConstraints.gridx = 1;
			xtfLogFileUiConstraints.gridy = 4;
			xtfLogFileUiConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
			doXtfLogFileSelBtnConstraints.gridx = 2;
			doXtfLogFileSelBtnConstraints.gridy = 4;
			
			// row 5
			configFileLabelConstraints.gridx = 0;
			configFileLabelConstraints.gridy = 5;
			configFileLabelConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
			configFileUiConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
			configFileUiConstraints.weightx = 1.0;
			configFileUiConstraints.gridx = 1;
			configFileUiConstraints.gridy = 5;
			configFileUiConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
			doConfigFileSelBtnConstraints.gridx = 2;
			doConfigFileSelBtnConstraints.gridy = 5;
			
			// row 6
			logPaneConstraints.fill = java.awt.GridBagConstraints.BOTH;
			logPaneConstraints.weightx = 1.0;
			logPaneConstraints.weighty = 1.0;
			logPaneConstraints.gridx = 0;
			logPaneConstraints.gridy = 6;
			logPaneConstraints.gridheight = 2;
			logPaneConstraints.gridwidth = 2;
			doValidateConstraints.gridy = 6;
			doValidateConstraints.gridx = 2;
			
			// row 7
			clearlogBtnConstraints.gridx = 2;
			clearlogBtnConstraints.gridy = 7;
			clearlogBtnConstraints.anchor = java.awt.GridBagConstraints.NORTH;
			
			jContentPane.setLayout(new java.awt.GridBagLayout());
			jContentPane.add(getXtfFileLabel(), xtfFileLabelConstraints);
			jContentPane.add(getXtfFileUi(), xtfFileUiConstraints);
			jContentPane.add(getDoXtfFileSelBtn(), doXtfFileSelBtnConstraints);
			
			jContentPane.add(getModelNamesLabel(), modelNamesLabelConstraints);
			jContentPane.add(getModelNamesUi(), modelNamesUiConstraints);
			
			jContentPane.add(getLogFileLabel(), logFileLabelConstraints);
			jContentPane.add(getLogFileUi(), logFileUiConstraints);
			jContentPane.add(getDoLogFileSelBtn(), doLogFileSelBtnConstraints);

			jContentPane.add(getXtfLogFileLabel(), xtfLogFileLabelConstraints);
			jContentPane.add(getXtfLogFileUi(), xtfLogFileUiConstraints);
			jContentPane.add(getDoXtfLogFileSelBtn(), doXtfLogFileSelBtnConstraints);
			
			jContentPane.add(getConfigFileLabel(), configFileLabelConstraints);
			jContentPane.add(getConfigFileUi(), configFileUiConstraints);
			jContentPane.add(getDoConfigFileSelBtn(), doConfigFileSelBtnConstraints);
			
			jContentPane.add(getJScrollPane(), logPaneConstraints);
			jContentPane.add(getClearlogBtn(), clearlogBtnConstraints);
			jContentPane.add(getAllObjectsAccessibleUi(), allObjectsAccessibleConstraints);
			jContentPane.add(getDoValidateBtn(), doValidateConstraints);
		}
		return jContentPane;
	}
	private javax.swing.JLabel getXtfFileLabel() {
		if(xtfFileLabel == null) {
			xtfFileLabel = new javax.swing.JLabel();
			xtfFileLabel.setText(rsrc.getString("MainFrame.xtfFileLabel"));
		}
		return xtfFileLabel;
	}
	private javax.swing.JLabel getModelNamesLabel() {
		if(modelNamesLabel == null) {
			modelNamesLabel = new javax.swing.JLabel();
			modelNamesLabel.setText(rsrc.getString("MainFrame.modelNamesLabel"));
		}
		return modelNamesLabel;
	}
	private javax.swing.JLabel getConfigFileLabel() {
		if(configFileLabel == null) {
			configFileLabel = new javax.swing.JLabel();
			configFileLabel.setText(rsrc.getString("MainFrame.configFileLabel"));
		}
		return configFileLabel;
	}
	private javax.swing.JLabel getLogFileLabel() {
		if(logFileLabel == null) {
			logFileLabel = new javax.swing.JLabel();
			logFileLabel.setText(rsrc.getString("MainFrame.logFileLabel"));
		}
		return logFileLabel;
	}
	private javax.swing.JLabel getXtfLogFileLabel() {
		if(xtfLogFileLabel == null) {
			xtfLogFileLabel = new javax.swing.JLabel();
			xtfLogFileLabel.setText(rsrc.getString("MainFrame.xtfLogFileLabel"));
		}
		return xtfLogFileLabel;
	}
	private javax.swing.JTextArea getXtfFileUi() {
		if(xtfFileUi == null) {
			xtfFileUi = new javax.swing.JTextArea();
		}
		return xtfFileUi;
	}
	private javax.swing.JTextField getModelNamesUi() {
		if(modelNamesUi == null) {
			modelNamesUi = new javax.swing.JTextField();
		}
		return modelNamesUi;
	}
	private javax.swing.JTextField getConfigFileUi() {
		if(configFileUi == null) {
			configFileUi = new javax.swing.JTextField();
		}
		return configFileUi;
	}
	private javax.swing.JTextField getLogFileUi() {
		if(logFileUi == null) {
			logFileUi = new javax.swing.JTextField();
		}
		return logFileUi;
	}
	private javax.swing.JTextField getXtfLogFileUi() {
		if(xtfLogFileUi == null) {
			xtfLogFileUi = new javax.swing.JTextField();
		}
		return xtfLogFileUi;
	}
	private javax.swing.JTextArea getLogUi() {
		if(logUi == null) {
			logUi = new javax.swing.JTextArea();
			logUi.setEditable(false);
		}
		return logUi;
	}
	private javax.swing.JButton getClearlogBtn() {
		if(clearlogBtn == null) {
			clearlogBtn = new javax.swing.JButton();
			clearlogBtn.setText(rsrc.getString("MainFrame.clearLogButton"));
			clearlogBtn.addActionListener(new java.awt.event.ActionListener() { 
				public void actionPerformed(java.awt.event.ActionEvent e) {    
					logClear();
				}
			});
		}
		return clearlogBtn;
	}
	private javax.swing.JCheckBox getAllObjectsAccessibleUi() {
		if(allObjectsAccessibleUi == null) {
			allObjectsAccessibleUi = new javax.swing.JCheckBox();
			allObjectsAccessibleUi.setText(rsrc.getString("MainFrame.allObjectsAccessible"));
		}
		return allObjectsAccessibleUi;
	}
	// selected files
	public String[] getXtfFile(){
		String fileTextInUi=getXtfFileUi().getText();
		String[] files=splitFilenames(fileTextInUi);
		return files;
	}
	/** unterteilt den mehrzeiligen String in einzelne Zeilen. 
	 * Leerzeilen werden nicht ins Resultat uebernommen.
	 * Leerzeichen am Anfang und Ende der einzelnen Zeilen werden entfernt.
	 * Leerzeichen innerhlab der Zeile werden nicht veraendert.
	 * @param lines der mehrzeilige String
	 * @return das array der einzelnen Zeilen
	 */
	public static String[] splitFilenames(String lines) {
		String[] dataFileParts=lines.split("\n");
		List<String> trimmedFileParts=new ArrayList<String>();
		for(String fileText:dataFileParts) {
			String trimmedText=fileText.trim();
			String retText=trimmedText.replace("\n", "");
			if(retText!=null && !retText.isEmpty() && retText.toString().length()>0) {
				trimmedFileParts.add(retText);
			}
		}
		String[] partArr = trimmedFileParts.toArray(new String[0]);
		return partArr;
	}
	public void setXtfFile(String[] xtfFileList){
		StringBuilder stringBuilder = new StringBuilder();
		String newLine="";
		for(int i=0;i<xtfFileList.length;i++){
			if(xtfFileList[i]!=null) {
				stringBuilder.append(newLine);
				stringBuilder.append(xtfFileList[i]);
				newLine = "\n";
			}
		}
		getXtfFileUi().setText(stringBuilder.toString());
	}
	// selected model names
	public String getModelNames() {
		return StringUtility.purge(getModelNamesUi().getText());
	}
	
	public void setModelNames(String modelNames){
		getModelNamesUi().setText(modelNames);
	}
	
	public String getConfigFile(){
		return StringUtility.purge(getConfigFileUi().getText());
	}
	public boolean getObjectsAccessible(){
		boolean allObjectsAccessible=getAllObjectsAccessibleUi().isSelected();
		return allObjectsAccessible;
	}
	public void setObjectsAccessible(boolean allObjectsAccessible){
		getAllObjectsAccessibleUi().setSelected(allObjectsAccessible);
	}
	public void setConfigFile(String dbhost){
		getConfigFileUi().setText(dbhost);
	}
	public String getLogFile(){
		return StringUtility.purge(getLogFileUi().getText());
	}
	public void setLogFile(String logfile){
		getLogFileUi().setText(logfile);
	}
	public String getXtfLogFile(){
		return StringUtility.purge(getXtfLogFileUi().getText());
	}
	public void setXtfLogFile(String xtflogfile){
		getXtfLogFileUi().setText(xtflogfile);
	}
	public Settings getSettings()
	{
		// get values from UI
		String logFile=getLogFile();
		String xtflogFile=getXtfLogFile();
		String configFile=getConfigFile();
		String modelNames=getModelNames();
		String objectsAccess=getObjectsAccessible()?Validator.TRUE:Validator.FALSE;
		
		// keep some values from current settings
		String workingDir=settings.getValue(ch.interlis.ili2c.gui.UserSettings.WORKING_DIRECTORY);
		String proxyHost=settings.getValue(ch.interlis.ili2c.gui.UserSettings.HTTP_PROXY_HOST);
		String proxyPort=settings.getValue(ch.interlis.ili2c.gui.UserSettings.HTTP_PROXY_PORT);
		String ilidirs=settings.getValue(Validator.SETTING_ILIDIRS);
        String appHome=settings.getValue(Validator.SETTING_APPHOME);

		
		Settings newSettings=new Settings();
		
		newSettings.setValue(ch.interlis.ili2c.gui.UserSettings.WORKING_DIRECTORY,workingDir);
		newSettings.setValue(Validator.SETTING_LOGFILE,logFile);
		newSettings.setValue(Validator.SETTING_XTFLOG,xtflogFile);
		newSettings.setValue(Validator.SETTING_MODELNAMES,modelNames);
		newSettings.setValue(Validator.SETTING_CONFIGFILE,configFile);
		newSettings.setValue(Validator.SETTING_ALL_OBJECTS_ACCESSIBLE,objectsAccess);
		newSettings.setValue(Validator.SETTING_ILIDIRS,ilidirs);
		newSettings.setValue(Validator.SETTING_APPHOME, appHome);
		newSettings.setValue(ch.interlis.ili2c.gui.UserSettings.HTTP_PROXY_HOST,proxyHost);
		newSettings.setValue(ch.interlis.ili2c.gui.UserSettings.HTTP_PROXY_PORT,proxyPort);
		return newSettings;
	}
	public void setSettings(Settings settings)
	{
		this.settings=settings;
	}
	private StringBuffer body=new StringBuffer();
	private javax.swing.JScrollPane jScrollPane = null;
	private javax.swing.JButton doValidateBtn = null;
	public void logAppend(String msg){
		body.append(msg);
		if(!msg.endsWith("\n")){
			body.append("\n");
		}
		getLogUi().setText(body.toString());
	}
	public void logClear(){
		body=new StringBuffer();
		getLogUi().setText(body.toString());
	}
	private javax.swing.JScrollPane getJScrollPane() {
		if(jScrollPane == null) {
			jScrollPane = new javax.swing.JScrollPane();
			jScrollPane.setViewportView(getLogUi());
		}
		return jScrollPane;
	}
	public static void main(String[] xtfFile,Settings settings) {
			MainFrame frame=new MainFrame();
			frame.setSettings(settings);
			String logFile=settings.getValue(Validator.SETTING_LOGFILE);
			frame.setLogFile(logFile);
			EhiLogger.getInstance().addListener(new LogListener(frame,logFile));
			frame.setXtfFile(xtfFile);
			String modelList=settings.getValue(Validator.SETTING_MODELNAMES);
			frame.setModelNames(modelList);
			String xtflogFile=settings.getValue(Validator.SETTING_XTFLOG);
			frame.setXtfLogFile(xtflogFile);
			String configFile=settings.getValue(Validator.SETTING_CONFIGFILE);
			frame.setConfigFile(configFile);
			frame.setObjectsAccessible(Validator.TRUE.equals(settings.getValue(Validator.SETTING_ALL_OBJECTS_ACCESSIBLE)));
			frame.show();
	}
	private javax.swing.JButton getDoValidateBtn() {
		if(doValidateBtn == null) {
			doValidateBtn = new javax.swing.JButton();
			doValidateBtn.setText(rsrc.getString("MainFrame.doValidateButton"));
			doValidateBtn.addActionListener(new java.awt.event.ActionListener() { 
				public void actionPerformed(java.awt.event.ActionEvent e) {
					SwingWorker worker = new SwingWorker() {
						public Object construct() {
							try {
								Validator.runValidation(getXtfFile(),getSettings());
							} catch (Exception ex) {
								EhiLogger.logError(rsrc.getString("MainFrame.generalError"),ex);
							}
							return null;
						}
					};
					worker.start();
				}
			});
		}
		return doValidateBtn;
	}
	private javax.swing.JButton getDoXtfFileSelBtn() {
		if(doXtfFileSelBtn == null) {
			doXtfFileSelBtn = new javax.swing.JButton();
			doXtfFileSelBtn.setText("...");
			doXtfFileSelBtn.addActionListener(new java.awt.event.ActionListener() { 
				public void actionPerformed(java.awt.event.ActionEvent e) {    
                    FileChooser fileDialog =  new FileChooser();
                    fileDialog.setCurrentDirectory(new File(getWorkingDirectory()));
                    fileDialog.setDialogTitle(rsrc.getString("MainFrame.xtfFileChooserTitle"));
                    fileDialog.addChoosableFileFilter(new GenericFileFilter(rsrc.getString("MainFrame.itfFileFilter"),"itf"));
                    fileDialog.addChoosableFileFilter(GenericFileFilter.createXmlFilter());
                    FileNameExtensionFilter filter = new FileNameExtensionFilter(rsrc.getString("MainFrame.xtfFileFilter"), "xtf", "xml");
                    fileDialog.setFileFilter(filter);
                    
                    fileDialog.setMultiSelectionEnabled(true);

					if (fileDialog.showOpenDialog(MainFrame.this) == FileChooser.APPROVE_OPTION) {
						setWorkingDirectory(fileDialog.getCurrentDirectory().getAbsolutePath());
						File[] multipleFiles = fileDialog.getSelectedFiles();
						String[] selectedFiles = new String[multipleFiles.length];
						for(int i=0;i<multipleFiles.length;i++){
							selectedFiles[i]=multipleFiles[i].getAbsolutePath();
						}
						setXtfFile(selectedFiles);
					}				
				}
			});
		}
		return doXtfFileSelBtn;
	}
	private javax.swing.JButton getDoLogFileSelBtn() {
		if(doLogFileSelBtn == null) {
			doLogFileSelBtn = new javax.swing.JButton();
			doLogFileSelBtn.setText("...");
			doLogFileSelBtn.addActionListener(new java.awt.event.ActionListener() { 
				public void actionPerformed(java.awt.event.ActionEvent e) {    
					String file=getLogFile();
					FileChooser fileDialog =  new FileChooser(file);
					fileDialog.setCurrentDirectory(new File(getWorkingDirectory()));
					fileDialog.setDialogTitle(rsrc.getString("MainFrame.logFileChooserTitle"));
					fileDialog.addChoosableFileFilter(new GenericFileFilter(rsrc.getString("MainFrame.logFileFilter"),"log"));
					fileDialog.addChoosableFileFilter(new GenericFileFilter(rsrc.getString("MainFrame.txtFileFilter"),"txt"));

					if (fileDialog.showSaveDialog(MainFrame.this) == FileChooser.APPROVE_OPTION) {
						setWorkingDirectory(fileDialog.getCurrentDirectory().getAbsolutePath());
						file=fileDialog.getSelectedFile().getAbsolutePath();
						setLogFile(file);
					}					
				}
			});
		}
		return doLogFileSelBtn;
	}
	private javax.swing.JButton getDoXtfLogFileSelBtn() {
		if(doXtfLogFileSelBtn == null) {
			doXtfLogFileSelBtn = new javax.swing.JButton();
			doXtfLogFileSelBtn.setText("...");
			doXtfLogFileSelBtn.addActionListener(new java.awt.event.ActionListener() { 
				public void actionPerformed(java.awt.event.ActionEvent e) {    
					String file=getLogFile();
					FileChooser fileDialog =  new FileChooser(file);
					fileDialog.setCurrentDirectory(new File(getWorkingDirectory()));
					fileDialog.setDialogTitle(rsrc.getString("MainFrame.xtflogFileChooserTitle"));
					fileDialog.addChoosableFileFilter(new GenericFileFilter(rsrc.getString("MainFrame.xtfFileFilter"),"xtf"));

					if (fileDialog.showSaveDialog(MainFrame.this) == FileChooser.APPROVE_OPTION) {
						setWorkingDirectory(fileDialog.getCurrentDirectory().getAbsolutePath());
						file=fileDialog.getSelectedFile().getAbsolutePath();
						setXtfLogFile(file);
					}					
				}
			});
		}
		return doXtfLogFileSelBtn;
	}
	private javax.swing.JButton getDoConfigFileSelBtn() {
		if(doConfigFileSelBtn == null) {
			doConfigFileSelBtn = new javax.swing.JButton();
			doConfigFileSelBtn.setText("...");
			doConfigFileSelBtn.addActionListener(new java.awt.event.ActionListener() { 
				public void actionPerformed(java.awt.event.ActionEvent e) {    
					String file=getLogFile();
					FileChooser fileDialog =  new FileChooser(file);
					fileDialog.setCurrentDirectory(new File(getWorkingDirectory()));
					fileDialog.setDialogTitle(rsrc.getString("MainFrame.configFileChooserTitle"));
					fileDialog.addChoosableFileFilter(new GenericFileFilter(rsrc.getString("MainFrame.configFileFilter"),"toml"));

					if (fileDialog.showOpenDialog(MainFrame.this) == FileChooser.APPROVE_OPTION) {
						setWorkingDirectory(fileDialog.getCurrentDirectory().getAbsolutePath());
						file=fileDialog.getSelectedFile().getAbsolutePath();
						setConfigFile(file);
					}					
				}
			});
		}
		return doConfigFileSelBtn;
	}
	private java.lang.String getWorkingDirectory() {
		String wd=settings.getValue(ch.interlis.ili2c.gui.UserSettings.WORKING_DIRECTORY);
		if(wd==null){
			wd=new File(".").getAbsolutePath();
		}
		return wd;
	}
	private void setWorkingDirectory(java.lang.String workingDirectory) {
		settings.setValue(ch.interlis.ili2c.gui.UserSettings.WORKING_DIRECTORY, workingDirectory);
	}
}
