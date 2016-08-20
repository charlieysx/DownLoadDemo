package com.smile.download.ui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.border.EmptyBorder;

import com.smile.download.dao.FileInfo;
import com.smile.download.services.DownLoad;

public class MainUI extends JFrame implements UpdateUi {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel jl_FileName;
	private JProgressBar jp_downing;
	private JLabel jl_DownLength;
	private JButton btn_Start;
	private JButton btn_Stop;

	private DownLoad downLoad;
	private int length = 0;;
	private int value = 0;
//	private long time;

	/**
	 * Create the frame.
	 */
	public MainUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setBounds(100, 100, 450, 200);
		setLocation((dim.width - getWidth()) / 2,
				(dim.height - getHeight()) / 2);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		jl_FileName = new JLabel("name");
		jl_FileName.setBounds(10, 10, 414, 32);
		contentPane.add(jl_FileName);

		jp_downing = new JProgressBar();
		jp_downing.setBounds(10, 52, 400, 15);
		jp_downing.setUI(new MyProgressBar(jp_downing));
		contentPane.add(jp_downing);

		jl_DownLength = new JLabel("0");
		jl_DownLength.setBounds(10, 76, 414, 32);
		contentPane.add(jl_DownLength);

		downLoad = new DownLoad(this);

		btn_Start = new JButton("Start");
		btn_Start.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				btn_Stop.setEnabled(true);
				btn_Start.setEnabled(false);
				value = 0;
//				time = System.currentTimeMillis();
				
				// 测试
				// 下载酷狗音乐
//				downLoad.startDownLoad(new FileInfo(0,
//						"http://download.kugou.com/download/kugou_pc",
//						"kugou windows版V 8090.exe", 0));

				// 下载本地的视频
				downLoad.startDownLoad(new FileInfo(0,
						"http://127.0.0.1:8181/img/mooc_video.mp4",
						"mooc_video.mp4", 0));
				//
				// 下载图片
				// downLoad.startDownLoad(new FileInfo(
				// 0,
				// "http://5.1015600.com/picture/pic/000/381/788997d733dc9a8edb07b8d094b0a064.jpg",
				// "788997d733dc9a8edb07b8d094b0a064.jpg", 0));

			}
		});

		btn_Start.setBounds(10, 118, 190, 32);
		btn_Start.setUI(new MyButton());
		contentPane.add(btn_Start);

		btn_Stop = new JButton("Stop");
		btn_Stop.setEnabled(false);
		btn_Stop.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				btn_Stop.setEnabled(false);
				btn_Start.setEnabled(true);
				downLoad.stopDownLoad();
			}
		});

		btn_Stop.setBounds(210, 118, 211, 32);
		btn_Stop.setUI(new MyButton());
		contentPane.add(btn_Stop);
	}

	@Override
	public void setFileName(String name) {
		jl_FileName.setText(name);
	}

	@Override
	public void setFileLength(int length) {
		this.length = length;
	}

	int how = 0;
	
	@Override
	public void updateProgress(int len) {
		synchronized (this) {
//			how++;
//			if(how == 1){
//				System.out.println(System.currentTimeMillis() - time);
//			}
			value += len;
			float now = (float) (value * 1.0 / 1024 / 1024);
			float all = (float) (length * 1.0 / 1024 / 1024);
			int p = (int) (now * 100 / all);

			jp_downing.setValue(p);
			jl_DownLength.setText(String.format(
					"%.2fM/%.2fM------------------%d%%", now, all, p));
			if (p == 100) {
				jl_FileName.setText(jl_FileName.getText().replace("正在下载",
						"下载完成"));
//				System.out.println(System.currentTimeMillis() - time);
				btn_Stop.setEnabled(false);
				btn_Start.setEnabled(false);
			}
		}
	}
}
