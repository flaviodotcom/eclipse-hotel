package br.com.mobicare.testcase.eclipsehotel.service;

import br.com.mobicare.testcase.eclipsehotel.model.Customer;
import br.com.mobicare.testcase.eclipsehotel.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    private static final Logger logger = LoggerFactory.getLogger(CustomerService.class);

    @Autowired
    private CustomerRepository customerRepository;

    public List<Customer> findAll() {
        logger.info("Procurando por todos os clientes");
        return customerRepository.findAll();
    }

    public Optional<Customer> findById(Long id) {
        logger.info("Procurando pelo cliente de id " + id);
        return customerRepository.findById(id);
    }

    public Customer save(Customer customer) {
        logger.info("Salvando as informações do cliente " + customer.getName());
        return customerRepository.save(customer);
    }

    public void deleteById(Long id) {
        logger.info("Excluindo as informações do cliente de id " + id);
        customerRepository.deleteById(id);
    }
}