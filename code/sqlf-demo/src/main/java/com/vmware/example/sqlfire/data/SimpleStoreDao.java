package com.vmware.example.sqlfire.data;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.jdbc.core.JdbcTemplate;

import com.vmware.example.sqlfire.domain.Order;
import com.vmware.example.sqlfire.domain.OrderItem;
import com.vmware.example.sqlfire.domain.Request;

public class SimpleStoreDao implements InitializingBean, StoreData {

	private static final Logger logger = LoggerFactory
			.getLogger(SimpleStoreDao.class);

	private JdbcTemplate dataSource;
	private String dataSourceContext;

	PreparedStatement selectRequestStatement;
	PreparedStatement insertRequestStatement;
	PreparedStatement updateRequestStatement;
	PreparedStatement insertOrderStatement;
	PreparedStatement updateOrderItemStatement;
	PreparedStatement deleteOrderStatement;
	PreparedStatement insertOrderItemStatement;
	PreparedStatement deleteOrderItemStatement;

	@Override
	public void afterPropertiesSet() throws Exception {

		Connection conn = dataSource.getDataSource().getConnection();

		insertRequestStatement = conn
				.prepareStatement("INSERT INTO "
						+ dataSourceContext
						+ ".REQUESTS (REQUEST_ID,REQUEST_ON,REQUEST_ORDERS,REQUEST_ITEMS,REQUEST_DURATION) VALUES (?,?,?,?,?)");
		insertOrderStatement = conn.prepareStatement("INSERT INTO "
				+ dataSourceContext
				+ ".ORDERS (ORDER_ID,REQUEST_ID,ORDER_ON) VALUES (?,?,?)");
		deleteOrderStatement = conn.prepareStatement("DELETE FROM "
				+ dataSourceContext + ".ORDERS WHERE REQUEST_ID = ?");
		insertOrderItemStatement = conn
				.prepareStatement("INSERT INTO "
						+ dataSourceContext
						+ ".ITEMS (ITEM_ID,ORDER_ID,ITEM_NAME,ITEM_PRICE) VALUES (?,?,?,?)");
		updateOrderItemStatement = conn.prepareStatement("UPDATE "
				+ dataSourceContext
				+ ".ITEMS SET ITEM_PRICE = ? WHERE ITEM_ID = ?");
		deleteOrderItemStatement = conn.prepareStatement("DELETE FROM "
				+ dataSourceContext + ".ITEMS WHERE ORDER_ID = ?");
		selectRequestStatement = conn.prepareStatement("SELECT * FROM "
				+ dataSourceContext + ".REQUESTS WHERE REQUEST_ID = ?");
		updateRequestStatement = conn
				.prepareStatement("UPDATE "
						+ dataSourceContext
						+ ".REQUESTS SET REQUEST_ORDERS = ?, REQUEST_ITEMS = ?, REQUEST_DURATION = ? WHERE REQUEST_ID = ?");

	}

	@Override
	public void saveRequest(Request request) {
		logger.debug("Insert: " + request);

		try {
			insertRequestStatement.setString(1, request.getId());
			insertRequestStatement.setTimestamp(2, request.getOn());
			insertRequestStatement.setInt(3, request.getOrders());
			insertRequestStatement.setInt(4, request.getItems());
			insertRequestStatement.setLong(5, request.getDuration());
			insertRequestStatement.execute();
		} catch (SQLException e) {
			logger.error(insertRequestStatement.toString());
			logger.error(e.getMessage());
			throw new RuntimeException(e);
		}
	}

	@Override
	public void updateRequestDuration(Request request) {
		logger.debug("Insert: " + request);

		try {
			updateRequestStatement.setInt(1, request.getOrders());
			updateRequestStatement.setInt(2, request.getItems());
			updateRequestStatement.setLong(3, request.getDuration());
			updateRequestStatement.setString(4, request.getId());
			updateRequestStatement.execute();
		} catch (SQLException e) {
			logger.error(updateRequestStatement.toString());
			logger.error(e.getMessage());
			throw new RuntimeException(e);
		}

	}

	@Override
	public Request getRequest(String id) {
		logger.debug("Request Id: " + id);

		Request request = null;
		try {
			selectRequestStatement.setString(1, id);
			ResultSet rs = selectRequestStatement.executeQuery();
			while (rs.next()) {
				request = new Request();
				request.setId(rs.getString("request_id"));
				request.setOn(rs.getTimestamp("request_on"));
				request.setOrders(rs.getInt("request_orders"));
				request.setItems(rs.getInt("request_items"));
				request.setDuration(rs.getLong("request_duration"));
			}
		} catch (SQLException e) {
			logger.error(selectRequestStatement.toString());
			logger.error(e.getMessage());
			throw new RuntimeException(e);
		}

		return request;

	}

	@Override
	public void saveOder(Request request, Order order) {
		logger.debug("Insert: " + order);

		try {
			// Order
			insertOrderStatement.setString(1, order.getId());
			insertOrderStatement.setString(2, request.getId());
			insertOrderStatement.setTimestamp(3, order.getOn());
			insertOrderStatement.execute();

			// NOTE: Could have used batch over here, but, for
			// purposes of this example I wanted use Statements

			for (OrderItem item : order.getItems()) {
				// Item Insert
				insertOrderItemStatement.setString(1, item.getId());
				insertOrderItemStatement.setString(2, order.getId());
				insertOrderItemStatement.setString(3, item.getName());
				insertOrderItemStatement.setBigDecimal(4, item.getPrice());
				insertOrderItemStatement.execute();
				// Item Update
				updateOrderItemStatement.setBigDecimal(1, new BigDecimal(item
						.getPrice().doubleValue() * 2));
				updateOrderItemStatement.setString(2, item.getId());
				updateOrderItemStatement.execute();
			}

		} catch (SQLException e) {
			logger.error(insertOrderStatement.toString());
			logger.error(insertOrderItemStatement.toString());
			logger.error(e.getMessage());
			throw new RuntimeException(e);
		}
	}

	@Override
	public void removeOders(String requestId) {
		logger.debug("Delete: " + requestId);

		try {
			deleteOrderStatement.setString(1, requestId);
			deleteOrderStatement.execute();
		} catch (SQLException e) {
			logger.error(deleteOrderStatement.toString());
			logger.error(e.getMessage());
			throw new RuntimeException(e);
		}
	}

	@Override
	public void removeOderItems(String orderId) {
		logger.debug("Delete: " + orderId);

		try {
			deleteOrderItemStatement.setString(1, orderId);
			deleteOrderItemStatement.execute();
		} catch (SQLException e) {
			logger.error(deleteOrderItemStatement.toString());
			logger.error(e.getMessage());
			throw new RuntimeException(e);
		}
	}

	@Required
	public void setDataSource(JdbcTemplate dataSource) {
		this.dataSource = dataSource;
	}

	@Required
	public void setDataSourceContext(String dataSourceContext) {
		this.dataSourceContext = dataSourceContext;
	}

}
