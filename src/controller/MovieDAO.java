package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import model.HiNewVO;
import model.MovieUpVO;
import model.MovieViewVo;
import model.UserBooks;
import model.UserChartVO;

public class MovieDAO {
	public boolean idpass = false;

	public int getStudentregiste(HiNewVO svo) throws Exception {
		// �� ������ ó���� ���� SQL ��
		// �ش�� �ʵ� no�κ��� �ڵ����� �����ǹǷ� �ʵ带 �� �ʿ䰡 ����.
		String dml = "insert into hinew " + "(name, ID ,Password ,Year, Month ,Day ,Phone,age)" + " values "
				+ "(?, ?, ?, ?, ?, ?, ?,?)";

		Connection con = null;
		PreparedStatement pstmt = null;
		int count = 0;
		LocalDate date = null;
		try {
			// �� DBUtil Ŭ������ getConnection( )�޼���� �����ͺ��̽��� ����
			con = DBUtil.getConnection();

			// �� �Է¹��� �л� ������ ó���ϱ� ���Ͽ� SQL������ ����
			pstmt = con.prepareStatement(dml); // ������ ���� ���
			pstmt.setString(1, svo.getName());
			pstmt.setString(2, svo.getID());
			pstmt.setString(3, svo.getPassword());
			pstmt.setInt(4, svo.getYear());
			pstmt.setString(5, svo.getMonth());
			pstmt.setString(6, svo.getDay());
			pstmt.setString(7, svo.getPhone());
			pstmt.setInt(8, svo.getAge());

			// �� SQL���� ������ ó�� ����� ����
			count = pstmt.executeUpdate(); // mysql���� �������(������Ʈ)�� �ϸ� �̹����� ����

		} catch (SQLException e) {
			System.out.println("e=[" + e + "]");
			alertDisplay(1, "���̵� �ߺ��˴ϴ� .", "���̵� �ߺ��˴ϴ�", "�ٸ����̵�� ���ּ���.");
			RootController.idError = true;
		} catch (Exception e) {
			System.out.println("e=[" + e + "]");
		} finally {
			try {
				// �� �����ͺ��̽����� ���ῡ ���Ǿ��� ������Ʈ�� ����
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
			}
		}
		return count;
	}

	// ���â ���÷���
	// �α��� â���� ���̵� �����Ͽ� ����� �Ǿ����� ���� �ְ� ���ش�.
	public int getHiNewInfoID(HiNewVO svo) {
		// �� �н����忡�� �ִ� ���̵� ã��
		String sqlPassword = "select ID from hinew where Password = ?";
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int count = 0;
		String rss = null;
		try {
			// �� DBUtil Ŭ������ getConnection( )�޼���� �����ͺ��̽��� ����
			System.out.println("1");
			con = DBUtil.getConnection();
			// �� �Է¹��� �л� ������ ó���ϱ� ���Ͽ� SQL������ ����
			pstmt = con.prepareStatement(sqlPassword); // ������ ���� ���
			System.out.println("2");
			pstmt.setString(1, svo.getPassword());
			System.out.println("3");
			rs = pstmt.executeQuery();
			System.out.println("4" + svo.getPassword());
			while (rs.next()) {
				rss = rs.getString(1);
				System.out.println(rss + " ���̵�--------------");
				if (rss.equals(svo.getID()))
					break;
			}

			if (rss.equals(svo.getID())) {
				System.out.println("�߉���!");
			}
			System.out.println(rss);
			if (!rss.equals(svo.getID())) {
				throw new SQLException();
			}
			// �� SQL���� ������ ó�� ����� ����
			// count= pstmt.executeUpdate(); //mysql���� �������(������Ʈ)�� �ϸ� �̹����� ����

		} catch (SQLException e) {
			RootController.idPasswordError = true;
			alertDisplay(1, "��й�ȣ�� ���̵� ����.", "��й�ȣ�� ���̵� ����.", "��й�ȣ�� ���̵� Ȯ�����ּ���.");
			System.out.println("e=[" + e + "]");
		} catch (Exception e) {
			RootController.idPasswordError = true;
			alertDisplay(1, "��й�ȣ�� ���̵� ����.", "��й�ȣ�� ���̵� ����.", "��й�ȣ�� ���̵� Ȯ�����ּ���.");
			System.out.println("e=[" + e + "]");
			idpass = true;
		} finally {
			try {
				// �� �����ͺ��̽����� ���ῡ ���Ǿ��� ������Ʈ�� ����
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				RootController.idPasswordError = true;
				alertDisplay(1, "��й�ȣ�� ���̵� ����.", "��й�ȣ�� ���̵� ����.", "��й�ȣ�� ���̵� Ȯ�����ּ���.");
				System.out.println("e=[" + e + "]");
			}
		}

		return count;
	}

	// �α��� â���� ��й�ȣ�� �����Ͽ� ����� �Ǿ����� ���� �ְ� ���ش�.
	public int getHiNewInfoPassword(HiNewVO svo) {
		// �� ���̵� ������ �ִ� ��й�ȣã��
		String sqlID = "select Password from hinew where ID = ?";
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String rss = null;
		int count = 0;
		try {
			// �� DBUtil Ŭ������ getConnection( )�޼���� �����ͺ��̽��� ����
			con = DBUtil.getConnection();
			// �� �Է¹��� �л� ������ ó���ϱ� ���Ͽ� SQL������ ����
			pstmt = con.prepareStatement(sqlID); // ������ ���� ���
			pstmt.setString(1, svo.getID());
			rs = pstmt.executeQuery();
			while (rs.next()) {
				rss = rs.getString(1);
				System.out.println(rss);
				System.out.println(svo.getPassword());
			}
			if (rss.equals(svo.getPassword())) {
				System.out.println("�߉���!");
			} else {
				if (idpass == false) {
					throw new SQLException();
				}
			}
			// �� SQL���� ������ ó�� ����� ����
			// count= pstmt.executeUpdate(); //mysql���� �������(������Ʈ)�� �ϸ� �̹����� ����

		} catch (SQLException e) {
			RootController.idPasswordError = true;
			alertDisplay(1, "��й�ȣ�� ���̵� ����.", "��й�ȣ�� ���̵� ����.", "��й�ȣ�� ���̵� Ȯ�����ּ���.");
			System.out.println("e=[" + e + "]");
		} catch (Exception e) {
			RootController.idPasswordError = true;
			if (idpass == false) {
				alertDisplay(1, "��й�ȣ�� ���̵� ����.", "��й�ȣ�� ���̵� ����.", "��й�ȣ�� ���̵� Ȯ�����ּ���.");
				System.out.println("e=[" + e + "]");
			}
		} finally {
			try {
				// �� �����ͺ��̽����� ���ῡ ���Ǿ��� ������Ʈ�� ����
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				RootController.idPasswordError = true;
				alertDisplay(1, "��й�ȣ�� ���̵� ����.", "��й�ȣ�� ���̵� ����.", "��й�ȣ�� ���̵� Ȯ�����ּ���.");
				System.out.println("e=[" + e + "]");
			}
		}

		return count;
	}

	/*
	 * //��ȭ�� ������ ���̽����� ���̺�� public ArrayList<MovieUpVO> getMoviewTable() {
	 * ArrayList<MovieUpVO> list = new ArrayList<MovieUpVO>(); String dml =
	 * "select * from movietb";
	 * 
	 * Connection con = null; PreparedStatement pstmt = null; ResultSet rs =
	 * null;//����Ÿ���̽� ���� �ӽ÷� �����ϴ� ��� �����ϴ� ��ü MovieUpVO mVo = null;
	 * 
	 * try { con = DBUtil.getConnection(); pstmt = con.prepareStatement(dml); rs =
	 * pstmt.executeQuery(); while (rs.next()) { mVo = new
	 * MovieUpVO(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
	 * rs.getString(5), rs.getInt(6)); list.add(mVo); } } catch (SQLException se) {
	 * System.out.println(se); } catch (Exception e) { System.out.println(e); }
	 * finally { try { if (rs != null) rs.close(); if (pstmt != null) pstmt.close();
	 * if (con != null) con.close(); } catch (SQLException se) { } } return list; }
	 */
	public static int getMovieregiste(MovieUpVO svo) throws Exception {
		// �� ������ ó���� ���� SQL ��
		// �ش�� �ʵ� no�κ��� �ڵ����� �����ǹǷ� �ʵ带 �� �ʿ䰡 ����.
		String dml = "insert into movietb " + "(MovieID,MovieName,MoviewTime,julgary,filedata,viewAge)" + " values "
				+ "(?,?,?,?,?,?)";

		Connection con = null;
		PreparedStatement pstmt = null;
		int count = 0;

		try {
			// �� DBUtil Ŭ������ getConnection( )�޼���� �����ͺ��̽��� ����
			con = DBUtil.getConnection();

			// �� �Է¹��� �л� ������ ó���ϱ� ���Ͽ� SQL������ ����
			pstmt = con.prepareStatement(dml); // ������ ���� ���
			pstmt.setString(1, svo.getMovieID());
			pstmt.setString(2, svo.getMovieName());
			pstmt.setString(3, svo.getMoviewTime());
			pstmt.setString(4, svo.getJulgary());
			pstmt.setString(5, svo.getFiledata());
			pstmt.setInt(6, svo.getViewAge());

			// �� SQL���� ������ ó�� ����� ����
			count = pstmt.executeUpdate(); // mysql���� �������(������Ʈ)�� �ϸ� �̹����� ����

		} catch (SQLException e) {
			System.out.println("e=[" + e + "]");
		} catch (Exception e) {
			System.out.println("e=[" + e + "]");
		} finally {
			try {
				// �� �����ͺ��̽����� ���ῡ ���Ǿ��� ������Ʈ�� ����
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return count;
	}

	public static void alertDisplay(int type, String title, String headerText, String contentText) {

		Alert alert = null; // ���� 1. Alert �̰��� ���� ������ �α��� ����� ���̾�α�â�� ���� ���������� �۾��� ������.

		switch (type) {
		case 1:
			alert = new Alert(AlertType.WARNING);
			break;
		case 2:
			alert = new Alert(AlertType.CONFIRMATION);
			break;
		case 3:
			alert = new Alert(AlertType.ERROR);
			break;
		case 4:
			alert = new Alert(AlertType.NONE);
			break;
		case 5:
			alert = new Alert(AlertType.INFORMATION);
			break;
		}

		alert.setTitle(title);
		alert.setHeaderText(headerText);
		alert.setContentText(contentText);
		alert.setResizable(false);
		alert.showAndWait();

	}

	public static ArrayList<MovieViewVo> getStudentTotal() {
		ArrayList<MovieViewVo> list = new ArrayList<MovieViewVo>();
		String dml = "select * from MovieViewList";

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;// ����Ÿ���̽� ���� �ӽ÷� �����ϴ� ��� �����ϴ� ��ü
		MovieViewVo emVo = null;

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(dml);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				emVo = new MovieViewVo(rs.getString(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getInt(5),
						rs.getString(6));
				list.add(emVo);
			}
		} catch (SQLException se) {
			System.out.println(se);
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (SQLException se) {
			}
		}
		return list;
	}

	public static int getMovieVieAction(MovieViewVo svo) {
		String dml = "insert into movieviewlist " + " values " + "(?,?,?,?,?,?)";

		Connection con = null;
		PreparedStatement pstmt = null;
		int count = 0;

		try {
			// �� DBUtil Ŭ������ getConnection( )�޼���� �����ͺ��̽��� ����
			con = DBUtil.getConnection();

			// �� �Է¹��� �л� ������ ó���ϱ� ���Ͽ� SQL������ ����
			pstmt = con.prepareStatement(dml); // ������ ���� ���
			pstmt.setString(1, svo.getMovieID());
			pstmt.setString(2, svo.getMovieName());
			pstmt.setString(3, svo.getMoviewTime());
			pstmt.setInt(4, svo.getMovieTotalPrice());
			pstmt.setInt(5, svo.getMovieTotalView());
			pstmt.setString(6, svo.getFileName());

			// �� SQL���� ������ ó�� ����� ����
			count = pstmt.executeUpdate(); // mysql���� �������(������Ʈ)�� �ϸ� �̹����� ����

		} catch (SQLException e) {
			System.out.println("e=[" + e + "]");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("e=[" + e + "]");
		} finally {
			try {
				// �� �����ͺ��̽����� ���ῡ ���Ǿ��� ������Ʈ�� ����
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return count;
	}

	public static int getMovieSuJung1(MovieUpVO svo,String s,String ss) {
		String dml = 

		 "update movieviewlist B\r\n" +
		 "SET MovieName=? ,MoviewTime=? ,"
		 + " Filename=?,MovieID=?\r\n" +
		 "WHERE MovieID= ?";
		// B.MovieName=? ,B.MovieTime=? , B.MovieTotalView=? , B.Filename=? ,
		// A.MovieName=? ,A.julgary=? , A.filedata=? , A.viewAge=?
		Connection con = null;
		PreparedStatement pstmt = null;
		int count = 0;

		try {
			// �� DBUtil Ŭ������ getConnection( )�޼���� �����ͺ��̽��� ����
			con = DBUtil.getConnection();

			// �� �Է¹��� �л� ������ ó���ϱ� ���Ͽ� SQL������ ����
			pstmt = con.prepareStatement(dml); // ������ ���� ���
			 pstmt.setString(1, svo.getMovieName());
			 pstmt.setString(2, svo.getMoviewTime());
			 pstmt.setString(3, ss);
			 pstmt.setString(4, svo.getMovieID());
			 pstmt.setString(5, s);

			// �� SQL���� ������ ó�� ����� ����
			count = pstmt.executeUpdate(); // mysql���� �������(������Ʈ)�� �ϸ� �̹����� ����

		} catch (SQLException e) {
			System.out.println("e=[" + e + "]");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("e=[" + e + "]");
		} finally {
			try {
				// �� �����ͺ��̽����� ���ῡ ���Ǿ��� ������Ʈ�� ����
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return count;
	}
	
	public static int getMovieSuJung(MovieUpVO svo,String s,String ss) {
		String dml = 
		"update movietb set MovieName= ?, julgary= ?, filedata= ?, viewAge=?,MovieID=?,MoviewTime=? WHERE MovieID= ?";
		 
		// B.MovieName=? ,B.MovieTime=? , B.MovieTotalView=? , B.Filename=? ,
		// A.MovieName=? ,A.julgary=? , A.filedata=? , A.viewAge=?
		Connection con = null;
		PreparedStatement pstmt = null;
		int count = 0;
		System.out.println(svo.getJulgary()+"666666666666666666666");
		try {
			// �� DBUtil Ŭ������ getConnection( )�޼���� �����ͺ��̽��� ����
			con = DBUtil.getConnection();

			// �� �Է¹��� �л� ������ ó���ϱ� ���Ͽ� SQL������ ����
			pstmt = con.prepareStatement(dml); // ������ ���� ���
			 pstmt.setString(1, svo.getMovieName());
			 pstmt.setString(2, svo.getJulgary());
			 pstmt.setString(3, ss);
			 pstmt.setInt(4, svo.getViewAge());
			 pstmt.setString(5, svo.getMovieID());
			 pstmt.setString(6, svo.getMoviewTime());
			 pstmt.setString(7,s);
			

			// �� SQL���� ������ ó�� ����� ����
			count = pstmt.executeUpdate(); // mysql���� �������(������Ʈ)�� �ϸ� �̹����� ����

		} catch (SQLException e) {
			System.out.println("e=[" + e + "]");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("e=[" + e + "]");
		} finally {
			try {
				// �� �����ͺ��̽����� ���ῡ ���Ǿ��� ������Ʈ�� ����
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return count;
	}
	
	public static ArrayList<MovieUpVO> getSujungTime(String s) {
		ArrayList<MovieUpVO> list = new ArrayList<MovieUpVO>();

		String dml = "select * from movietb where movieid=?";

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;// ����Ÿ���̽� ���� �ӽ÷� �����ϴ� ��� �����ϴ� ��ü
		MovieUpVO emVo = null;

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(dml);
			pstmt.setString(1, s);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				emVo = new MovieUpVO(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getString(5), rs.getInt(6));
				list.add(emVo);
			}
		} catch (SQLException se) {
			System.out.println(se);
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (SQLException se) {
			}
		}
		return list;

	}

	public static ArrayList<MovieViewVo> getMovieTime(String s) {
		ArrayList<MovieViewVo> list = new ArrayList<MovieViewVo>();

		String dml = "select * FROM movieviewlist WHERE MovieID LIKE ?;";
		String ss = "%" + s + "%";
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;// ����Ÿ���̽� ���� �ӽ÷� �����ϴ� ��� �����ϴ� ��ü
		MovieViewVo emVo = null;

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(dml);
			pstmt.setString(1, ss);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				emVo = new MovieViewVo(rs.getString(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getInt(5),
						rs.getString(6));
				list.add(emVo);
			}
			emVo.getFileName();
		} catch (SQLException se) {
			System.out.println(se);
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (SQLException se) {
			}
		}
		return list;

	}
	
	public static void deleteSujung(String s) {
		String dml = "delete from movietb where MovieID = ?";

		Connection con = null;
		PreparedStatement pstmt = null;
		int count = 0;

		try {
			// �� DBUtil Ŭ������ getConnection( )�޼���� �����ͺ��̽��� ����
			con = DBUtil.getConnection();

			// �� �Է¹��� �л� ������ ó���ϱ� ���Ͽ� SQL������ ����
			pstmt = con.prepareStatement(dml); // ������ ���� ���
	pstmt.setString(1, s);

			// �� SQL���� ������ ó�� ����� ����
			count = pstmt.executeUpdate(); // mysql���� �������(������Ʈ)�� �ϸ� �̹����� ����

		} catch (SQLException e) {
			System.out.println("e=[" + e + "]");
		} catch (Exception e) {
			System.out.println("e=[" + e + "]");
		} finally {
			try {
				// �� �����ͺ��̽����� ���ῡ ���Ǿ��� ������Ʈ�� ����
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	public static void deleteSujung1(String s) {
		String dml = "delete from movieviewlist where MovieID = ?";

		Connection con = null;
		PreparedStatement pstmt = null;
		int count = 0;

		try {
			// �� DBUtil Ŭ������ getConnection( )�޼���� �����ͺ��̽��� ����
			con = DBUtil.getConnection();

			// �� �Է¹��� �л� ������ ó���ϱ� ���Ͽ� SQL������ ����
			pstmt = con.prepareStatement(dml); // ������ ���� ���
	pstmt.setString(1, s);

			// �� SQL���� ������ ó�� ����� ����
			count = pstmt.executeUpdate(); // mysql���� �������(������Ʈ)�� �ϸ� �̹����� ����

		} catch (SQLException e) {
			System.out.println("e=[" + e + "]");
		} catch (Exception e) {
			System.out.println("e=[" + e + "]");
		} finally {
			try {
				// �� �����ͺ��̽����� ���ῡ ���Ǿ��� ������Ʈ�� ����
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}
	public static ArrayList<HiNewVO> getUser() {
		ArrayList<HiNewVO> list = new ArrayList<HiNewVO>();

		String dml = "select * FROM hinew";
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;// ����Ÿ���̽� ���� �ӽ÷� �����ϴ� ��� �����ϴ� ��ü
		HiNewVO emVo = null;

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(dml);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				emVo = new HiNewVO(rs.getString(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getString(5),
						rs.getString(6),rs.getString(7),rs.getInt(8));
				list.add(emVo);
			}
		} catch (SQLException se) {
			System.out.println(se);
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (SQLException se) {
			}
		}
		return list;

	}
	public static ArrayList<MovieUpVO> getMovieInfo(String s){
		ArrayList<MovieUpVO> list = new ArrayList<MovieUpVO>();
		String dml = "select * from movietb where Filedata like ? limit 1";
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;// ����Ÿ���̽� ���� �ӽ÷� �����ϴ� ��� �����ϴ� ��ü
		MovieUpVO emVo = null;

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(dml);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				emVo = new MovieUpVO(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
						rs.getInt(6));
				list.add(emVo);
			}
		} catch (SQLException se) {
			System.out.println(se);
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (SQLException se) {
			}
		}
		return list;
	}
	
	public static ArrayList<MovieUpVO> getMovieJugery(String filedata){
		ArrayList<MovieUpVO> list=new ArrayList<MovieUpVO>();
		String dml = "select * from movietb where Movieid = ?";
		System.out.println(filedata+"=filedata");
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;// ����Ÿ���̽� ���� �ӽ÷� �����ϴ� ��� �����ϴ� ��ü
		MovieUpVO emVo = null;

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(dml);
			pstmt.setString(1, filedata);
			rs = pstmt.executeQuery();
			System.out.println("999999999999999999999999999");
			while (rs.next()) {
				emVo = new MovieUpVO(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
						rs.getInt(6));
				System.out.println(rs.getString(1));
				System.out.println(rs.getString(2));
				System.out.println(rs.getString(3));
				System.out.println(rs.getString(4));
				System.out.println(rs.getString(5));
				System.out.println(rs.getInt(6));
				
				list.add(emVo);
			}
		} catch (SQLException se) {
			se.printStackTrace();
			System.out.println(se);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e);
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (SQLException se) {
			}
		}
		
		return list;
	}
	public static void UpdateUserGoods(UserBooks books) {
		// �� ������ ó���� ���� SQL ��
				// �ش�� �ʵ� no�κ��� �ڵ����� �����ǹǷ� �ʵ带 �� �ʿ䰡 ����.
				String dml = "insert into UserMovieBooks " +" values "
						+ "(?, ?, ?, ?,?)";

				Connection con = null;
				PreparedStatement pstmt = null;
				int count = 0;
				LocalDate date = null;
				try {
					// �� DBUtil Ŭ������ getConnection( )�޼���� �����ͺ��̽��� ����
					con = DBUtil.getConnection();

					// �� �Է¹��� �л� ������ ó���ϱ� ���Ͽ� SQL������ ����
					pstmt = con.prepareStatement(dml); // ������ ���� ���
					pstmt.setString(1, books.getUserID());
					pstmt.setString(2, books.getMovieID());
					pstmt.setString(3, books.getMovieTime());
					pstmt.setString(4, books.getMovieName());
					
					pstmt.setInt(5, books.getMoviePrice());
					

					// �� SQL���� ������ ó�� ����� ����
					count = pstmt.executeUpdate(); // mysql���� �������(������Ʈ)�� �ϸ� �̹����� ����

				} catch (SQLException e) {
					System.out.println("e=[" + e + "]");
					alertDisplay(1, "���̵� �ߺ��˴ϴ� .", "���̵� �ߺ��˴ϴ�", "�ٸ����̵�� ���ּ���.");
					RootController.idError = true;
				} catch (Exception e) {
					System.out.println("e=[" + e + "]");
				} finally {
					try {
						// �� �����ͺ��̽����� ���ῡ ���Ǿ��� ������Ʈ�� ����
						if (pstmt != null)
							pstmt.close();
						if (con != null)
							con.close();
					} catch (SQLException e) {
					}
				}
				
	}
	
	public static ArrayList<UserBooks> getUserGoodsTable(String s) {
		ArrayList<UserBooks> list=new ArrayList<UserBooks>();
		String dml = "select * from UserMovieBooks where userid = ?";
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;// ����Ÿ���̽� ���� �ӽ÷� �����ϴ� ��� �����ϴ� ��ü
		UserBooks emVo = null;

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(dml);
			pstmt.setString(1, s);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				emVo = new UserBooks(rs.getString(1), rs.getString(2), rs.getString(3),rs.getString(4),rs.getInt(5));
				list.add(emVo);
			}
		} catch (SQLException se) {
			se.printStackTrace();
			System.out.println(se);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e);
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (SQLException se) {
			}
		}
		return list;
	}
	public static ArrayList<UserBooks> getUserGoodsTableTotal() {
		ArrayList<UserBooks> list=new ArrayList<UserBooks>();
		String dml = "select * from UserMovieBooks";
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;// ����Ÿ���̽� ���� �ӽ÷� �����ϴ� ��� �����ϴ� ��ü
		UserBooks emVo = null;

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(dml);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				emVo = new UserBooks(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),rs.getInt(5));
				list.add(emVo);
			}
		} catch (SQLException se) {
			se.printStackTrace();
			System.out.println(se);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e);
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (SQLException se) {
			}
		}
		return list;
	}
	public static void deleteUser(String s) {
		String dml = "delete from hinew where ID = ?";

		Connection con = null;
		PreparedStatement pstmt = null;
		int count = 0;

		try {
			// �� DBUtil Ŭ������ getConnection( )�޼���� �����ͺ��̽��� ����
			con = DBUtil.getConnection();

			// �� �Է¹��� �л� ������ ó���ϱ� ���Ͽ� SQL������ ����
			pstmt = con.prepareStatement(dml); // ������ ���� ���
	pstmt.setString(1, s);

			// �� SQL���� ������ ó�� ����� ����
			count = pstmt.executeUpdate(); // mysql���� �������(������Ʈ)�� �ϸ� �̹����� ����

		} catch (SQLException e) {
			System.out.println("e=[" + e + "]");
		} catch (Exception e) {
			System.out.println("e=[" + e + "]");
		} finally {
			try {
				// �� �����ͺ��̽����� ���ῡ ���Ǿ��� ������Ʈ�� ����
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}
	public static ArrayList<UserChartVO> getChartElement() {
		ArrayList<UserChartVO> list=new ArrayList<UserChartVO>();
		String dml = "select MovieName,sum(MoviePrice) from UserMovieBooks group by MovieName order by sum(MoviePrice) desc limit 3;";
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;// ����Ÿ���̽� ���� �ӽ÷� �����ϴ� ��� �����ϴ� ��ü
		UserChartVO emVo = null;

		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(dml);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				emVo = new UserChartVO(rs.getString(1),rs.getInt(2));
				list.add(emVo);
			}
		} catch (SQLException se) {
			se.printStackTrace();
			System.out.println(se);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e);
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (SQLException se) {
			}
		}
		return list;
	}
	public static void deleteUserGoods(String s) {
		String dml = "delete from UserMovieBooks where MovieID = ?";

		Connection con = null;
		PreparedStatement pstmt = null;
		int count = 0;

		try {
			// �� DBUtil Ŭ������ getConnection( )�޼���� �����ͺ��̽��� ����
			con = DBUtil.getConnection();

			// �� �Է¹��� �л� ������ ó���ϱ� ���Ͽ� SQL������ ����
			pstmt = con.prepareStatement(dml); // ������ ���� ���
			pstmt.setString(1, s);

			// �� SQL���� ������ ó�� ����� ����
			count = pstmt.executeUpdate(); // mysql���� �������(������Ʈ)�� �ϸ� �̹����� ����

		} catch (SQLException e) {
			System.out.println("e=[" + e + "]");
		} catch (Exception e) {
			System.out.println("e=[" + e + "]");
		} finally {
			try {
				// �� �����ͺ��̽����� ���ῡ ���Ǿ��� ������Ʈ�� ����
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}
	public static String getError(String s) {
		String dml = "select MovieName,count(MovieID) from UserMovieBooks where MovieID = ?";

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;// ����Ÿ���̽� ���� �ӽ÷� �����ϴ� ��� �����ϴ� ��ü
		MovieUpVO emVo = null;
		String count = null;
		try {
			con = DBUtil.getConnection();
			pstmt = con.prepareStatement(dml);
			pstmt.setString(1, s);
			rs = pstmt.executeQuery();
			System.out.println("999999999999999999999999999");
			while (rs.next()) {
				count=rs.getString(2);
				System.out.println(count);
			}
		} catch (SQLException se) {
			se.printStackTrace();
			System.out.println(se);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e);
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (SQLException se) {
			}
		}
		return count;
	}
}