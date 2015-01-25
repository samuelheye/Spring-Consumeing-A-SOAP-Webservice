package hello;

import java.text.SimpleDateFormat;

import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

import hello.wsdl.Forecast;
import hello.wsdl.ForecastReturn;
import hello.wsdl.GetCityForecastByZIP;
import hello.wsdl.GetCityForecastByZIPResponse;
import hello.wsdl.GetCityWeatherByZIP;
import hello.wsdl.GetCityWeatherByZIPResponse;
import hello.wsdl.Temp;
import hello.wsdl.WeatherReturn;

public class WeatherClient extends WebServiceGatewaySupport {
	public GetCityForecastByZIPResponse getCityForecastByZip(String zipCode) {
		GetCityForecastByZIP request = new GetCityForecastByZIP();
		request.setZIP(zipCode);
		System.out.println();
		System.out.println("Requesting forecast for " + zipCode);
		GetCityForecastByZIPResponse response = (GetCityForecastByZIPResponse) getWebServiceTemplate()
				.marshalSendAndReceive(
						request,
						new SoapActionCallback(
								"http://ws.cdyne.com/WeatherWS/GetCityForecastByZIP"));
		return response;
	}
	
	public GetCityWeatherByZIPResponse getCityWeatherByZip(String zipCode) {
		GetCityWeatherByZIP request = new GetCityWeatherByZIP();
		request.setZIP(zipCode);
		System.out.println();
		System.out.println("Requesting weather for " + zipCode);
		GetCityWeatherByZIPResponse response = (GetCityWeatherByZIPResponse) getWebServiceTemplate()
				.marshalSendAndReceive(
						request,
						new SoapActionCallback(
								"http://ws.cdyne.com/WeatherWS/GetCityWeatherByZIP"));
		return response;
	}

	public void printResponse(GetCityForecastByZIPResponse response) {
		ForecastReturn forecastReturn = response
				.getGetCityForecastByZIPResult();
		if (forecastReturn.isSuccess()) {
			System.out.println();
			System.out.println("Forecast for " + forecastReturn.getCity()
					+ ", " + forecastReturn.getState());
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			for (Forecast forecast : forecastReturn.getForecastResult()
					.getForecast()) {
				System.out.print(format.format(forecast.getDate()
						.toGregorianCalendar().getTime()));
				System.out.print(" ");
				System.out.print(forecast.getDesciption());
				System.out.print(" ");
				Temp temperature = forecast.getTemperatures();
				System.out.print(temperature.getMorningLow() + "\u00b0-"
						+ temperature.getDaytimeHigh() + "\u00b0 ");
				System.out.println();
			}
		} else {
			System.out.println("No forecast received");
		}
	}
	
	public void printResponse(GetCityWeatherByZIPResponse response) {
		WeatherReturn weatherReturn = response
				.getGetCityWeatherByZIPResult();
		if (weatherReturn.isSuccess()) {
			System.out.println();
			System.out.println("Weather for " + weatherReturn.getCity()
					+ ", " + weatherReturn.getState());
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			
				System.out.print(" ");
				System.out.print(weatherReturn.getDescription());
				System.out.print(" ");				
				System.out.print(weatherReturn.getTemperature() + "\u00b0");
				System.out.print(", ");
				System.out.print("Relative Humidity "+ weatherReturn.getRelativeHumidity());
				System.out.print(", ");
				System.out.print("Pressure "+ weatherReturn.getPressure());
				
				
				System.out.println();
			
		} else {
			System.out.println("No forecast received");
		}
	}
}