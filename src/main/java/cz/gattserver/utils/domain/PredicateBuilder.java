package cz.gattserver.utils.domain;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

import java.util.Collection;

import org.apache.commons.lang3.StringUtils;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.dsl.BeanPath;
import com.querydsl.core.types.dsl.ComparableExpression;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.SimpleExpression;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.jpa.impl.JPAQuery;

public class PredicateBuilder {

	private BooleanBuilder booleanBuilder;

	public PredicateBuilder() {
		this.booleanBuilder = new BooleanBuilder();
	}

	/**
	 * Přidání porovnání je null, empty nebo mezera.
	 * 
	 * @param path
	 *            cesta k atributu entity
	 * @return this pro řetězení
	 */
	public PredicateBuilder isBlank(StringPath path) {
		booleanBuilder.and(path.isNull().or(path.isEmpty()).or(path.eq(" ")));
		return this;
	}

	/**
	 * Přidání porovnání je null nebo 0
	 * 
	 * @param path
	 *            cesta k atributu entity
	 * @return this pro řetězení
	 */
	public PredicateBuilder eqNullOrZero(NumberExpression<Long> path) {
		booleanBuilder.and(path.isNull().or(path.eq(0L)));
		return this;
	}

	/**
	 * Přidání porovnání není null, není empty a není mezera.
	 * 
	 * @param path
	 *            cesta k atributu entity
	 * @return this pro řetězení
	 */
	public PredicateBuilder neEmpty(StringExpression path) {
		booleanBuilder.and(path.isNotNull());
		booleanBuilder.and(path.isNotEmpty());
		booleanBuilder.and(path.ne(" "));
		return this;
	}

	/**
	 * A > B || B == null
	 * 
	 * Je potřeba pro případy porovnání x > null, pak je výsledek totiž false, s porovnáním na "nebo null" pak takové
	 * případy vychází
	 * 
	 * @param expression1
	 * @param expression2
	 * @return this pro řetězení
	 */
	public <T extends Comparable<T>> PredicateBuilder gtOrValueNull(ComparableExpression<T> expression1,
			ComparableExpression<T> expression2) {
		if (expression2 != null)
			booleanBuilder.and(ExpressionUtils.or(expression1.gt(expression2), expression2.isNull()));
		return this;
	}

	public <T extends Number & Comparable<?>> PredicateBuilder gtOrValueNull(NumberExpression<T> expression1,
			NumberExpression<T> expression2) {
		if (expression2 != null)
			booleanBuilder.and(ExpressionUtils.or(expression1.gt(expression2), expression2.isNull()));
		return this;
	}

	private boolean isNotNullAndNotBlank(Object o) {
		if (o == null)
			return false;
		return (!(o instanceof String && StringUtils.isBlank((String) o)));
	}

	/*
	 * EQ
	 */

	public <T> PredicateBuilder eq(SimpleExpression<T> expression, T value) {
		if (isNotNullAndNotBlank(value))
			booleanBuilder.and(expression.eq(value));
		return this;
	}

	/*
	 * NE
	 */

	public <T> PredicateBuilder ne(SimpleExpression<T> expression, T value) {
		if (isNotNullAndNotBlank(value))
			booleanBuilder.and(ExpressionUtils.or(expression.isNull(), expression.ne(value)));
		return this;
	}

	/*
	 * GT
	 */

	public <T extends Comparable<T>> PredicateBuilder gt(ComparableExpression<T> expression, T value) {
		if (isNotNullAndNotBlank(value))
			booleanBuilder.and(expression.gt(value));
		return this;
	}

	public <T extends Number & Comparable<?>> PredicateBuilder gt(NumberExpression<T> expression, T value) {
		if (isNotNullAndNotBlank(value))
			booleanBuilder.and(expression.gt(value));
		return this;
	}

	/*
	 * GE
	 */

	public <T extends Comparable<T>> PredicateBuilder ge(ComparableExpression<T> expression, T value) {
		if (isNotNullAndNotBlank(value))
			booleanBuilder.and(expression.goe(value));
		return this;
	}

	public <T extends Number & Comparable<?>> PredicateBuilder ge(NumberExpression<T> expression, T value) {
		if (isNotNullAndNotBlank(value))
			booleanBuilder.and(expression.goe(value));
		return this;
	}

	/*
	 * LT
	 */

	public <T extends Comparable<T>> PredicateBuilder lt(ComparableExpression<T> expression, T value) {
		if (isNotNullAndNotBlank(value))
			booleanBuilder.and(expression.lt(value));
		return this;
	}

	public <T extends Number & Comparable<?>> PredicateBuilder lt(NumberExpression<T> expression, T value) {
		if (isNotNullAndNotBlank(value))
			booleanBuilder.and(expression.lt(value));
		return this;
	}

	/*
	 * LE
	 */

	public <T extends Comparable<T>> PredicateBuilder le(ComparableExpression<T> expression, T value) {
		if (isNotNullAndNotBlank(value))
			booleanBuilder.and(expression.loe(value));
		return this;
	}

	public <T extends Number & Comparable<?>> PredicateBuilder le(NumberExpression<T> expression, T value) {
		if (isNotNullAndNotBlank(value))
			booleanBuilder.and(expression.loe(value));
		return this;
	}
	/*
	 * NOT IN
	 */

	public <T extends Comparable<T>> PredicateBuilder notIn(SimpleExpression<T> expression, Collection<T> values) {
		if (values != null)
			booleanBuilder.and(expression.notIn(values));
		return this;
	}

	/*
	 * IN
	 */

	public <T extends Comparable<T>> PredicateBuilder in(SimpleExpression<T> expression, Collection<T> values) {
		if (values != null)
			booleanBuilder.and(expression.in(values));
		return this;
	}

	/**
	 * Přidání porovnání NOT EXISTS.
	 * 
	 * @param query
	 *            sub query
	 * @return this pro řetězení
	 */
	public PredicateBuilder notExists(JPAQuery<?> query) {
		if (query != null)
			booleanBuilder.and(query.notExists());
		return this;
	}

	/**
	 * Přidání porvnání EXISTS.
	 * 
	 * @param query
	 *            sub query
	 * @return this pro řetězení
	 */
	public PredicateBuilder exists(JPAQuery<?> query) {
		if (query != null)
			booleanBuilder.and(query.exists());
		return this;
	}

	/**
	 * Přidání porvnání IS NULL.
	 * 
	 * @param expression
	 *            výraz k porovnání
	 * @return this pro řetězení
	 */
	public PredicateBuilder isNull(BeanPath<?> expression) {
		if (expression != null)
			booleanBuilder.and(expression.isNull());
		return this;
	}

	/**
	 * Přidání porvnání IS NULL nebo je ' '.
	 * 
	 * @param expression
	 *            výraz k porovnání
	 * @return this pro řetězení
	 */
	public PredicateBuilder isEmpty(StringExpression expression) {
		if (expression != null)
			booleanBuilder.and(
					ExpressionUtils.or(expression.eq(""), ExpressionUtils.or(expression.isNull(), expression.eq(" "))));
		return this;
	}

	/**
	 * Přidání porvnání IS NOT NULL a zároveň není ' '.
	 * 
	 * @param expression
	 *            výraz k porovnání
	 * @return this pro řetězení
	 */
	public PredicateBuilder isNotEmpty(StringExpression expression) {
		if (expression != null) {
			booleanBuilder.and(expression.isNotNull());
			booleanBuilder.and(expression.ne(" "));
			booleanBuilder.and(expression.ne(""));
		}
		return this;
	}

	/**
	 * Přidání porvnání IS NOT NULL.
	 * 
	 * @param expression
	 *            výraz k porovnání
	 * @return this pro řetězení
	 */
	public PredicateBuilder isNotNull(NumberExpression<?> expression) {
		if (expression != null)
			booleanBuilder.and(expression.isNotNull());
		return this;
	}

	/**
	 * Přidání porvnání IS NOT NULL.
	 * 
	 * @param expression
	 *            výraz k porovnání
	 * @return this pro řetězení
	 */
	public PredicateBuilder isNotNull(BeanPath<?> expression) {
		if (expression != null)
			booleanBuilder.and(expression.isNotNull());
		return this;
	}

	public static String prepareForLike(String value) {
		// nahradí znaky * znakem % pro SQL a JPQL vyhledávání v LIKE a navíc
		// přidá ještě jednou % aby se smazal rozdíl mezi údaji v DB, které mají
		// za sebou mezery a údaji v aplikaci, které se zadávají bez mezer
		return value.replace('*', '%') + '%';
	}

	/**
	 * Přidá porovnání SQL IGNORE CASE LIKE.
	 * 
	 * @param expression
	 *            výraz k porovnání
	 * @param value
	 *            hodnota pro porovnání
	 * @return this pro řetězení
	 */
	public PredicateBuilder iLike(StringExpression expression, String value) {
		if (isNotBlank(value) && !"*".equals(value))
			booleanBuilder.and(expression.likeIgnoreCase(prepareForLike(value)));
		return this;
	}

	/**
	 * Přidá porovnání SQL IGNORE CASE LIKE s tím, že se text může nacházet uprostřed
	 * 
	 * @param expression
	 *            výraz k porovnání
	 * @param value
	 *            hodnota pro porovnání
	 * @return this pro řetězení
	 */
	public PredicateBuilder anyILike(StringExpression expression, String value) {
		if (isNotBlank(value) && !"*".equals(value))
			booleanBuilder.and(expression.likeIgnoreCase(prepareForLike("*" + value)));
		return this;
	}

	/**
	 * Přidá porovnání SQL NOT IGNORE CASE LIKE.
	 * 
	 * @param expression
	 *            výraz k porovnání
	 * @param value
	 *            hodnota pro porovnání
	 * @return this pro řetězení
	 */
	public PredicateBuilder notILike(StringExpression expression, String value) {
		if (isNotBlank(value))
			booleanBuilder.andNot(expression.likeIgnoreCase(prepareForLike(value)));
		return this;
	}

	/**
	 * Přidá porovnání SQL LIKE.
	 * 
	 * @param expression
	 *            výraz k porovnání
	 * @param value
	 *            hodnota pro porovnání
	 * @return this pro řetězení
	 */
	public PredicateBuilder like(NumberExpression<?> expression, String value) {
		if (isNotBlank(value))
			booleanBuilder.and(expression.like(prepareForLike(value)));
		return this;
	}

	/**
	 * Přidá porovnání SQL LIKE.
	 * 
	 * @param path
	 *            cesta k atributu entity
	 * @param value
	 *            hodnota pro porovnání
	 * @return this pro řetězení
	 */
	public PredicateBuilder like(NumberPath<Long> path, Long value) {
		if (value != null)
			like(path, value.toString());
		return this;
	}

	/*
	 * BETWEEN
	 */

	public PredicateBuilder between(StringPath path, String from, String to) {
		if (isNotBlank(from) && isNotBlank(to)) {
			booleanBuilder.and(path.between(from, to));
		} else if (isNotBlank(from)) {
			iLike(path, prepareForLike(from));
		} else if (isNotBlank(to)) {
			iLike(path, prepareForLike(to));
		}
		return this;
	}

	public <T extends Comparable<T>> PredicateBuilder between(ComparableExpression<T> expression, T from, T to) {
		if (from != null && to != null) {
			booleanBuilder.and(expression.between(from, to));
		} else if (from != null) {
			booleanBuilder.and(expression.eq(from));
		} else if (to != null) {
			booleanBuilder.and(expression.eq(to));
		}
		return this;
	}

	/**
	 * Vrací celkový objekt predicate pro použítí v dotazu.
	 * 
	 * @return builder
	 */
	public BooleanBuilder getBuilder() {
		return booleanBuilder;
	}

}
