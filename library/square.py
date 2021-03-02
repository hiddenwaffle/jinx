from ansible.module_utils.basic import AnsibleModule


def run_module():
    module_args = {
        'x': {
            'type': 'int',
            'required': True
        }
    }
    module = AnsibleModule(argument_spec=module_args, supports_check_mode=False)
    result = {
        'x_squared': module.params['x'] ** 2
    }
    module.exit_json(**result)


def main():
    run_module()


if __name__ == '__main__':
    main()
